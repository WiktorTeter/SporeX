#include "net_client.h"
#include "config.h"

#include <Arduino.h>
#include <WiFi.h>
#include <WiFiClientSecure.h>
#include <HTTPClient.h>
#include <WebServer.h>
#include <DNSServer.h>
#include <Preferences.h>
#include <time.h>

static Preferences prefs;
static WebServer server(80);
static DNSServer dnsServer;

static bool setupMode = false;
static bool serverStarted = false;
static String savedSsid;
static String savedPass;
static uint32_t lastReconnectAttempt = 0;

// Cached WiFi list so the setup page opens faster
static String cachedOptionsHtml = "<option value=''>Scanning...</option>";

// ---------- WiFi event logging ----------
static void onWiFiEvent(WiFiEvent_t event) {
  Serial.print("[WiFi event] ");
  Serial.println((int)event);
}

// ---------- Helpers ----------
static bool wifiConnected() {
  return WiFi.status() == WL_CONNECTED;
}

bool netIsConnected() {
  return wifiConnected();
}

bool netIsInSetupMode() {
  return setupMode;
}

static String htmlEscape(const String& s) {
  String out;
  for (size_t i = 0; i < s.length(); i++) {
    char c = s[i];
    switch (c) {
      case '&': out += "&amp;"; break;
      case '<': out += "&lt;"; break;
      case '>': out += "&gt;"; break;
      case '"': out += "&quot;"; break;
      case '\'': out += "&#39;"; break;
      default: out += c; break;
    }
  }
  return out;
}

static void loadSavedCredentials() {
  prefs.begin("sporex-net", true);
  savedSsid = prefs.getString("ssid", "");
  savedPass = prefs.getString("pass", "");
  prefs.end();

  Serial.print("[NET] Saved SSID: ");
  Serial.println(savedSsid.length() ? savedSsid : "(none)");
}

static void saveCredentials(const String& ssid, const String& pass) {
  prefs.begin("sporex-net", false);
  prefs.putString("ssid", ssid);
  prefs.putString("pass", pass);
  prefs.end();

  savedSsid = ssid;
  savedPass = pass;

  Serial.println("[NET] WiFi credentials saved");
}

static void clearCredentials() {
  prefs.begin("sporex-net", false);
  prefs.clear();
  prefs.end();

  savedSsid = "";
  savedPass = "";

  Serial.println("[NET] WiFi credentials cleared");
}

static String wlStatusToText(wl_status_t s) {
  switch (s) {
    case WL_IDLE_STATUS: return "WL_IDLE_STATUS";
    case WL_NO_SSID_AVAIL: return "WL_NO_SSID_AVAIL";
    case WL_SCAN_COMPLETED: return "WL_SCAN_COMPLETED";
    case WL_CONNECTED: return "WL_CONNECTED";
    case WL_CONNECT_FAILED: return "WL_CONNECT_FAILED";
    case WL_CONNECTION_LOST: return "WL_CONNECTION_LOST";
    case WL_DISCONNECTED: return "WL_DISCONNECTED";
    default: return "UNKNOWN";
  }
}

static void printScanResults() {
  Serial.println("[SCAN] Scanning for WiFi...");
  int n = WiFi.scanNetworks();

  if (n <= 0) {
    Serial.println("[SCAN] No networks found");
    return;
  }

  Serial.print("[SCAN] Networks found: ");
  Serial.println(n);

  for (int i = 0; i < n; i++) {
    Serial.print("[SCAN] ");
    Serial.print(i);
    Serial.print(": SSID='");
    Serial.print(WiFi.SSID(i));
    Serial.print("' RSSI=");
    Serial.print(WiFi.RSSI(i));
    Serial.print(" ENC=");
    Serial.println((int)WiFi.encryptionType(i));
  }
}

static void refreshCachedNetworkList() {
  Serial.println("[SETUP] Refreshing cached WiFi list...");
  int n = WiFi.scanNetworks();

  if (n <= 0) {
    cachedOptionsHtml = "<option value=''>No networks found</option>";
    Serial.println("[SETUP] No networks found");
    return;
  }

  String options;
  for (int i = 0; i < n; i++) {
    String ssid = WiFi.SSID(i);
    if (ssid.length() == 0) continue;

    Serial.print("[SETUP] Option SSID: ");
    Serial.println(ssid);

    options += "<option value=\"" + htmlEscape(ssid) + "\">";
    options += htmlEscape(ssid) + " (" + String(WiFi.RSSI(i)) + " dBm)";
    options += (WiFi.encryptionType(i) == WIFI_AUTH_OPEN ? " Open" : " Secured");
    options += "</option>";
  }

  if (options.length() == 0) {
    options = "<option value=''>No visible networks found</option>";
  }

  cachedOptionsHtml = options;
}

static bool scanContainsSSID(const String& target) {
  int n = WiFi.scanNetworks();
  if (n <= 0) return false;

  for (int i = 0; i < n; i++) {
    if (WiFi.SSID(i) == target) {
      return true;
    }
  }
  return false;
}

static bool connectToWiFi(const String& ssid, const String& pass) {
  if (ssid.isEmpty()) {
    Serial.println("[NET] Empty SSID");
    return false;
  }

  WiFi.mode(WIFI_STA);
  WiFi.setSleep(false);
  WiFi.disconnect(true, true);
  delay(500);

  printScanResults();

  if (!scanContainsSSID(ssid)) {
    Serial.print("[NET] Target SSID not found in scan: ");
    Serial.println(ssid);
    return false;
  }

  Serial.print("[NET] Connecting to WiFi: ");
  Serial.println(ssid);

  WiFi.begin(ssid.c_str(), pass.c_str());

  uint32_t start = millis();
  wl_status_t lastStatus = WiFi.status();

  while ((millis() - start) < WIFI_CONNECT_TIMEOUT_MS) {
    wl_status_t s = WiFi.status();

    if (s != lastStatus) {
      Serial.print("[NET] WiFi status changed: ");
      Serial.print((int)s);
      Serial.print(" (");
      Serial.print(wlStatusToText(s));
      Serial.println(")");
      lastStatus = s;
    }

    if (s == WL_CONNECTED) {
      break;
    }

    delay(500);
    Serial.print(".");
  }
  Serial.println();

  if (!wifiConnected()) {
    wl_status_t s = WiFi.status();
    Serial.print("[NET] WiFi connect failed, status = ");
    Serial.print((int)s);
    Serial.print(" (");
    Serial.print(wlStatusToText(s));
    Serial.println(")");
    return false;
  }

  Serial.print("[NET] Connected. IP: ");
  Serial.println(WiFi.localIP());
  return true;
}

static bool connectToSavedWiFi() {
  if (savedSsid.isEmpty()) {
    Serial.println("[NET] No saved WiFi credentials");
    return false;
  }
  return connectToWiFi(savedSsid, savedPass);
}

// ---------- NTP ----------
static bool syncTimeWithNTP() {
  configTime(GMT_OFFSET_SEC, DAYLIGHT_OFFSET_SEC, NTP_SERVER);

  Serial.print("[NTP] Syncing time");
  time_t now = 0;
  int retries = 0;

  while (now < 1700000000 && retries < 20) {
    delay(500);
    Serial.print(".");
    time(&now);
    retries++;
  }
  Serial.println();

  if (now < 1700000000) {
    Serial.println("[NTP] Failed to sync time");
    return false;
  }

  Serial.print("[NTP] Time synced. Epoch: ");
  Serial.println((uint32_t)now);
  return true;
}

// ---------- Web UI ----------
static String buildHomePage() {
  String html;
  html += "<!DOCTYPE html><html><head><meta name='viewport' content='width=device-width,initial-scale=1'>";
  html += "<title>SporeX Device</title>";
  html += "<style>";
  html += "body{font-family:Arial,sans-serif;max-width:420px;margin:40px auto;padding:16px;}";
  html += "h2{margin-bottom:8px;}p{margin:8px 0;}button{margin-top:16px;padding:12px 16px;font-size:16px;width:100%;}";
  html += ".card{border:1px solid #ddd;border-radius:10px;padding:16px;margin-bottom:16px;}";
  html += "</style></head><body>";
  html += "<h2>SporeX Device</h2>";

  html += "<div class='card'>";
  html += "<p><b>Mode:</b> ";
  html += setupMode ? "Setup Mode" : "Connected Mode";
  html += "</p>";

  if (wifiConnected()) {
    html += "<p><b>Connected WiFi:</b> " + htmlEscape(WiFi.SSID()) + "</p>";
    html += "<p><b>Device IP:</b> " + WiFi.localIP().toString() + "</p>";
  } else {
    html += "<p><b>Setup WiFi:</b> " + String(SETUP_AP_SSID) + "</p>";
    html += "<p><b>Setup IP:</b> " + WiFi.softAPIP().toString() + "</p>";
  }

  html += "</div>";

  html += "<div class='card'>";
  html += "<form method='GET' action='/wifi'>";
  html += "<button type='submit'>Set Up WiFi</button>";
  html += "</form>";
  html += "<form method='GET' action='/reset'>";
  html += "<button type='submit'>Reset WiFi</button>";
  html += "</form>";
  html += "</div>";

  html += "</body></html>";
  return html;
}

static String buildSetupPage() {
  String html;
  html += "<!DOCTYPE html><html><head><meta name='viewport' content='width=device-width,initial-scale=1'>";
  html += "<title>SporeX WiFi Setup</title>";
  html += "<style>";
  html += "body{font-family:Arial,sans-serif;max-width:420px;margin:40px auto;padding:16px;}";
  html += "h2{margin-bottom:8px;}label{display:block;margin-top:14px;margin-bottom:6px;}";
  html += "select,input{width:100%;padding:12px;font-size:16px;box-sizing:border-box;}";
  html += "button{margin-top:18px;padding:12px 16px;font-size:16px;width:100%;}";
  html += ".note{margin-top:12px;color:#444;font-size:14px;}";
  html += "</style></head><body>";
  html += "<h2>Set Up WiFi</h2>";
  html += "<p>Select your WiFi and enter the password.</p>";
  html += "<form method='POST' action='/save'>";
  html += "<label for='ssid'>WiFi Network</label>";
  html += "<select name='ssid' id='ssid'>" + cachedOptionsHtml + "</select>";
  html += "<label for='password'>Password</label>";
  html += "<input type='password' name='password' id='password' placeholder='Enter WiFi password'>";
  html += "<button type='submit'>Save and Connect</button>";
  html += "</form>";
  html += "<form method='GET' action='/refresh-networks'><button type='submit'>Refresh Network List</button></form>";
  html += "<form method='GET' action='/'><button type='submit'>Back</button></form>";
  html += "<form method='GET' action='/reset'><button type='submit'>Reset WiFi</button></form>";
  html += "<p class='note'>Reset WiFi will clear the saved network and restart the device.</p>";
  html += "</body></html>";

  return html;
}

static void handleRoot() {
  if (setupMode) {
    server.sendHeader("Location", "/wifi", true);
    server.send(302, "text/plain", "");
    return;
  }

  server.send(200, "text/html", buildHomePage());
}

static void handleWiFiPage() {
  server.send(200, "text/html", buildSetupPage());
}

static void handleRefreshNetworks() {
  refreshCachedNetworkList();
  server.sendHeader("Location", "/wifi", true);
  server.send(302, "text/plain", "");
}

static void handleSave() {
  if (!server.hasArg("ssid")) {
    server.send(400, "text/plain", "Missing SSID");
    return;
  }

  String ssid = server.arg("ssid");
  String pass = server.arg("password");

  Serial.print("[SETUP] Submitted SSID: ");
  Serial.println(ssid);

  if (ssid.isEmpty()) {
    server.send(400, "text/plain", "SSID cannot be empty");
    return;
  }

  saveCredentials(ssid, pass);

  String html;
  html += "<!DOCTYPE html><html><head><meta name='viewport' content='width=device-width,initial-scale=1'>";
  html += "<title>SporeX WiFi Saved</title></head><body style='font-family:Arial;max-width:420px;margin:40px auto;padding:16px;'>";
  html += "<h2>WiFi Saved</h2>";
  html += "<p>The device saved the WiFi settings for <b>" + htmlEscape(ssid) + "</b>.</p>";
  html += "<p>The device will now restart and connect.</p>";
  html += "</body></html>";

  server.send(200, "text/html", html);
  delay(1500);
  ESP.restart();
}

static void handleReset() {
  Serial.println("[WEB] Reset WiFi requested");

  clearCredentials();

  String html;
  html += "<!DOCTYPE html><html><head><meta name='viewport' content='width=device-width,initial-scale=1'>";
  html += "<title>SporeX WiFi Reset</title></head><body style='font-family:Arial;max-width:420px;margin:40px auto;padding:16px;'>";
  html += "<h2>WiFi Reset</h2>";
  html += "<p>The saved WiFi credentials have been cleared.</p>";
  html += "<p>The device will restart and return to setup mode.</p>";
  html += "</body></html>";

  server.send(200, "text/html", html);
  delay(1500);
  ESP.restart();
}

static void handleCaptivePortalRedirect() {
  server.sendHeader("Cache-Control", "no-cache, no-store, must-revalidate");
  server.sendHeader("Pragma", "no-cache");
  server.sendHeader("Expires", "-1");
  server.sendHeader("Location", "http://192.168.4.1/wifi", true);
  server.send(302, "text/plain", "");
}

static void handleNotFound() {
  handleCaptivePortalRedirect();
}

static void startWebServer() {
  if (serverStarted) return;

  server.on("/", HTTP_GET, handleRoot);
  server.on("/wifi", HTTP_GET, handleWiFiPage);
  server.on("/refresh-networks", HTTP_GET, handleRefreshNetworks);
  server.on("/save", HTTP_POST, handleSave);
  server.on("/reset", HTTP_GET, handleReset);

  // Android captive portal checks
  server.on("/generate_204", HTTP_GET, handleCaptivePortalRedirect);
  server.on("/gen_204", HTTP_GET, handleCaptivePortalRedirect);

  // Windows captive portal checks
  server.on("/connecttest.txt", HTTP_GET, handleCaptivePortalRedirect);
  server.on("/ncsi.txt", HTTP_GET, handleCaptivePortalRedirect);

  // Apple captive portal checks
  server.on("/hotspot-detect.html", HTTP_GET, handleCaptivePortalRedirect);
  server.on("/canonical.html", HTTP_GET, handleCaptivePortalRedirect);
  server.on("/library/test/success.html", HTTP_GET, handleCaptivePortalRedirect);
  server.on("/success.txt", HTTP_GET, handleCaptivePortalRedirect);

  // Generic
  server.on("/redirect", HTTP_GET, handleCaptivePortalRedirect);

  server.onNotFound(handleNotFound);
  server.begin();

  serverStarted = true;
  Serial.println("[WEB] Server started");
}

static void startSetupPortal() {
  setupMode = true;

  WiFi.disconnect(true, true);
  delay(300);

  WiFi.mode(WIFI_AP_STA);
  delay(300);

  bool ok = WiFi.softAP(SETUP_AP_SSID, SETUP_AP_PASS);
  Serial.print("[SETUP] softAP start result: ");
  Serial.println(ok ? "OK" : "FAIL");

  IPAddress apIP = WiFi.softAPIP();
  Serial.print("[SETUP] AP started. IP: ");
  Serial.println(apIP);

  refreshCachedNetworkList();

  dnsServer.start(53, "*", apIP);
  startWebServer();

  Serial.println("[SETUP] Connect phone to AP or scan setup QR");
  Serial.println("[SETUP] Captive portal should open automatically");
  Serial.println("[SETUP] Fallback URL: http://192.168.4.1/wifi");
}

static void stopSetupPortal() {
  if (!setupMode) return;

  dnsServer.stop();
  WiFi.softAPdisconnect(true);
  setupMode = false;

  Serial.println("[SETUP] Portal stopped");
}

// ---------- Public API ----------
bool netInit() {
  WiFi.onEvent(onWiFiEvent);

  loadSavedCredentials();

  if (connectToSavedWiFi()) {
    stopSetupPortal();
    startWebServer();

    Serial.print("[WEB] Open device page at http://");
    Serial.println(WiFi.localIP());

    return syncTimeWithNTP();
  }

  startSetupPortal();
  return false;
}

void netLoop() {
  if (setupMode) {
    dnsServer.processNextRequest();
  }

  if (serverStarted) {
    server.handleClient();
  }

  if (setupMode) return;
  if (wifiConnected()) return;

  uint32_t now = millis();
  if (now - lastReconnectAttempt < WIFI_RETRY_INTERVAL_MS) return;
  lastReconnectAttempt = now;

  Serial.println("[NET] WiFi disconnected, retrying saved network...");

  if (connectToSavedWiFi()) {
    syncTimeWithNTP();
    Serial.print("[WEB] Open device page at http://");
    Serial.println(WiFi.localIP());
  } else {
    Serial.println("[NET] Reconnect failed, entering setup mode");
    startSetupPortal();
  }
}

bool netSend(const Reading& r) {
  if (!wifiConnected()) {
    Serial.println("[NET] Not connected, skip send");
    return false;
  }

  if (r.co2 == 0) {
    Serial.println("[NET] Invalid reading, skip send");
    return false;
  }

  time_t now;
  time(&now);
  if (now < 1700000000) {
    Serial.println("[NTP] Time invalid, re-syncing...");
    if (!syncTimeWithNTP()) {
      Serial.println("[NET] Cannot send without valid time");
      return false;
    }
    time(&now);
  }

  const uint32_t ts = (uint32_t)now;

  String json;
  json.reserve(160);
  json += "{";
  json += "\"device_id\":\"" + String(DEVICE_ID) + "\",";
  json += "\"co2\":" + String(r.co2) + ",";
  json += "\"temp_c\":" + String(r.tempC, 2) + ",";
  json += "\"humidity\":" + String(r.rh, 2) + ",";
  json += "\"ts\":" + String(ts);
  json += "}";

  WiFiClientSecure client;
  client.setInsecure();

  HTTPClient http;
  http.setTimeout(30000);
  http.setReuse(false);

  Serial.print("[NET] POST ");
  Serial.println(API_URL);

  if (!http.begin(client, API_URL)) {
    Serial.println("[NET] http.begin failed");
    return false;
  }

  http.addHeader("Content-Type", "application/json");
  http.addHeader("X-Device-Token", DEVICE_INGEST_TOKEN);

  int httpCode = http.POST((uint8_t*)json.c_str(), json.length());
  Serial.print("[NET] HTTP ");
  Serial.println(httpCode);

  if (httpCode < 0) {
    Serial.print("[NET] HTTP error: ");
    Serial.println(http.errorToString(httpCode));
    http.end();
    return false;
  }

  String resp = http.getString();
  if (resp.length() > 0) {
    Serial.print("[NET] Response: ");
    Serial.println(resp);
  }

  http.end();
  return (httpCode >= 200 && httpCode < 300);
}