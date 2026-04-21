package com.example.sporex_app.ui.alerts

object VentilationLogic {

    fun getMessage(settings: VentilationSettings): AlertMessage {

        val isAirStale = settings.ventCount < 2 || settings.reminderIntervalHours >= 3

        return if (isAirStale) {
            AlertMessage(
                title = "Ventilation Needed",
                body = "Air has been stagnant. Open windows for 10–15 minutes."
            )
        } else {
            AlertMessage(
                title = "Air Quality Good",
                body = "Everything looks fresh."
            )
        }
    }
}

data class AlertMessage(
    val title: String,
    val body: String
)