package com.example.sporex_app.ui.device

sealed class DeviceScreen(val route: String) {
    object DeviceSetup : DeviceScreen("device_setup")
    object DeviceEdit : DeviceScreen("device_edit")
}
