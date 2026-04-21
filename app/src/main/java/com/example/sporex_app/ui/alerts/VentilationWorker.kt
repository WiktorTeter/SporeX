package com.example.sporex_app.ui.alerts

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class VentilationWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {

        Log.d("VentilationWorker", "WORKER FIRED")
        val prefs = applicationContext.getSharedPreferences(
            "ventilation",
            Context.MODE_PRIVATE
        )

        val settings = VentilationSettings(
            ventCount = prefs.getInt("vent_count", 1),
            hasHumidifier = prefs.getBoolean("humidifier", false),
            reminderIntervalHours = prefs.getInt("interval", 3)
        )

        val message = VentilationLogic.getMessage(settings)

        NotificationHelper.sendNotification(
            applicationContext,
            title = message.title,
            message = message.body
        )

        return Result.success()
    }
}