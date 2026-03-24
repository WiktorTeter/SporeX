package com.example.sporex_app.settings

data class Settings(
    val dark_mode: Boolean = false,
    val notifications_enabled: Boolean = true,
    val data_personalisation: Boolean = true,
    val app_customisation: Map<String,String> = emptyMap()
)

data class SettingsResponse(
    val success: Boolean,
    val settings: Settings
)

data class UpdateSettingsRequest(
    val email: String,
    val settings: Settings
)