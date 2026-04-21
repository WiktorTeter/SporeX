package com.example.sporex_app.ui.alerts



data class VentilationSettings(
    val reminderIntervalHours: Int = 3,
    val maxWindowOpenMinutes: Int = 60,
    val hasHumidifier: Boolean = false,
    val ventCount: Int = 1
)

