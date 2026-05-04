#include <Arduino.h>

#include "config.h"
#include "sensors.h"
#include "display_ui.h"
#include "net_client.h"

static uint32_t lastSensorMs = 0;
static uint32_t lastSendMs = 0;
static uint32_t lastWifiScreenMs = 0;

static bool sensorsReady = false;
static Reading latest;

void setup() {
  Serial.begin(115200);
  delay(200);

  if (!displayInit()) {
    Serial.println("OLED failed");
    while (1) delay(100);
  }

  displayBoot("Booting...", "Starting WiFi");

  if (!netInit()) {
    displayWiFiWaiting();
  } else {
    displayBoot("WiFi OK", "Starting sensors");
  }
}

void loop() {
  netLoop();

  uint32_t now = millis();

  // Do not start sensors until WiFi is connected
  if (!netIsConnected()) {
    if (now - lastWifiScreenMs >= 3000) {
      lastWifiScreenMs = now;
      displayWiFiWaiting();
    }
    return;
  }

  // Start sensors only once after WiFi is connected
  if (!sensorsReady) {
    Serial.println("[SENSORS] WiFi connected, starting sensors...");

    if (!sensorsInit()) {
      displayError("SCD41 init failed");
      while (1) delay(100);
    }

    sensorsReady = true;
    displayBoot("Sensors OK", "Reading data...");
    delay(1000);
  }

  if (now - lastSensorMs >= SENSOR_INTERVAL_MS) {
    lastSensorMs = now;

    if (sensorsRead(latest)) {
      Serial.print("CO2 [ppm]: ");
      Serial.println(latest.co2);

      Serial.print("Temp [C]: ");
      Serial.println(latest.tempC);

      Serial.print("RH [%]: ");
      Serial.println(latest.rh);

      Serial.println("---");

      displayReadings(latest);
    } else {
      Serial.println("Data not ready yet...");
    }
  }

  if (now - lastSendMs >= SEND_INTERVAL_MS) {
    lastSendMs = now;
    netSend(latest);
  }
}