#include "display_ui.h"
#include "config.h"

#include <SPI.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SH110X.h>
#include <math.h>

static Adafruit_SH1107 display(
  SCREEN_WIDTH,
  SCREEN_HEIGHT,
  &SPI,
  OLED_DC,
  OLED_RST,
  OLED_CS
);

// ---------- Helpers ----------
static void drawHLine(int y) {
  display.drawLine(4, y, SCREEN_WIDTH - 5, y, SH110X_WHITE);
}

static void drawCenteredText(const String& text, int y, int textSize) {
  int16_t x1, y1;
  uint16_t w, h;

  display.setTextSize(textSize);
  display.getTextBounds(text, 0, y, &x1, &y1, &w, &h);

  int x = (SCREEN_WIDTH - w) / 2;
  if (x < 0) x = 0;

  display.setCursor(x, y);
  display.print(text);
}

static String getTempStatus(float tempC) {
  if (tempC < 10.0f) return "LOW";
  if (tempC > 25.0f) return "HIGH";
  return "OK";
}

static String getHumidityStatus(float rh) {
  if (rh < 40.0f) return "LOW";
  if (rh > 60.0f) return "HIGH";
  return "OK";
}

static String getCO2Status(uint16_t co2) {
  if (co2 > 1200) return "HIGH";
  if (co2 < 600) return "LOW";
  return "OK";
}

static String getMouldRisk(const Reading& r) {
  if (r.rh > 70.0f) return "HIGH";
  if (r.rh > 60.0f && r.co2 > 1000) return "HIGH";
  if (r.rh < 40.0f) return "LOW";

  if (r.rh > 60.0f || r.co2 > 1200 || r.tempC > 25.0f || r.tempC < 10.0f) {
    return "MED";
  }

  return "OK";
}

static String getRecommendedAction(const Reading& r) {
  String risk = getMouldRisk(r);

  if (risk == "HIGH") {
    if (r.rh > 60.0f && r.co2 > 1000) return "Open window";
    if (r.rh > 60.0f) return "Ventilate";
    if (r.tempC < 10.0f) return "Warm room";
    if (r.tempC > 25.0f) return "Cool room";
    return "Airflow";
  }

  if (risk == "MED") {
    if (r.rh > 60.0f) return "Ventilate";
    if (r.co2 > 1200) return "Open window";
    if (r.tempC < 10.0f) return "Reduce damp";
    if (r.tempC > 25.0f) return "Monitor";
    return "Check room";
  }

  if (risk == "LOW") {
    return "Air dry";
  }

  return "Normal";
}

static void drawRow(int y, const char* label, const String& value, const String& status) {
  // Label
  display.setTextSize(1);
  display.setCursor(4, y + 6);
  display.print(label);

  // Value
  display.setTextSize(2);
  display.setCursor(36, y);
  display.print(value);

  // Status
  display.setTextSize(1);
  int16_t x1, y1;
  uint16_t w, h;
  display.getTextBounds(status, 0, y, &x1, &y1, &w, &h);

  int x = SCREEN_WIDTH - 6 - w;
  if (x < 96) x = 96;

  display.setCursor(x, y + 6);
  display.print(status);
}

// ---------- Public API ----------
bool displayInit() {
  SPI.begin(OLED_CLK, -1, OLED_MOSI, OLED_CS);

  if (!display.begin()) {
    Serial.println("SH1107 init failed");
    return false;
  }

  display.setRotation(0);
  display.clearDisplay();
  display.display();
  return true;
}

void displayBoot(const char* line1, const char* line2) {
  display.clearDisplay();
  display.setTextColor(SH110X_WHITE);

  drawCenteredText(line1, 24, 2);

  if (line2) {
    drawCenteredText(line2, 62, 1);
  }

  display.display();
}

void displayWiFiWaiting() {
  display.clearDisplay();
  display.setTextColor(SH110X_WHITE);

  drawCenteredText("Connect", 16, 2);
  drawCenteredText("to WiFi", 42, 2);
  drawHLine(68);
  drawCenteredText("Join SporeX-Setup", 80, 1);
  drawCenteredText("Setup opens auto", 96, 1);

  display.display();
}

void displayReadings(const Reading& r) {
  const String tempStatus = getTempStatus(r.tempC);
  const String humStatus  = getHumidityStatus(r.rh);
  const String co2Status  = getCO2Status(r.co2);
  const String risk       = getMouldRisk(r);
  const String action     = getRecommendedAction(r);

  display.clearDisplay();
  display.setTextColor(SH110X_WHITE);

  drawCenteredText("ROOM STATUS", 2, 1);
  drawHLine(12);

  drawRow(16, "Temp", String(r.tempC, 1) + "C", tempStatus);
  drawHLine(34);

  drawRow(38, "Hum", String((int)round(r.rh)) + "%", humStatus);
  drawHLine(56);

  drawRow(60, "CO2", String(r.co2), co2Status);
  drawHLine(78);

  display.setTextSize(1);

  display.setCursor(4, 88);
  display.print("Risk: ");
  display.print(risk);

  display.setCursor(4, 104);
  display.print("Action: ");
  display.print(action);

  display.display();
}

void displayError(const char* msg) {
  display.clearDisplay();
  display.setTextColor(SH110X_WHITE);

  drawCenteredText("ERROR", 18, 2);
  drawHLine(46);

  display.setTextSize(1);
  display.setCursor(6, 62);
  display.println(msg);

  display.display();
}