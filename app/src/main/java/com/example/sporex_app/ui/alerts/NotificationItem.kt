package com.example.sporex_app.ui.alerts


enum class AlertType {
    INFO,
    WARNING,
    CRITICAL
}

data class NotificationItem(
    val title: String,
    val message: String,
    val time: String,
    val type: AlertType
)