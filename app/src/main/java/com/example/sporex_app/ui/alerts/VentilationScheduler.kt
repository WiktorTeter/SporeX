//package com.example.sporex_app.ui.alerts
//
//import android.content.Context
//import androidx.work.*
//import java.util.concurrent.TimeUnit
//object VentilationScheduler {
//
//    fun schedule(context: Context, intervalHours: Int) {
//
//        val workRequest =
//            PeriodicWorkRequestBuilder<VentilationWorker>(
//                intervalHours.toLong(), TimeUnit.HOURS
//            ).build()
//
//        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
//            "ventilation_reminder",
//            ExistingPeriodicWorkPolicy.REPLACE,
//            workRequest
//        )
//    }
//}

package com.example.sporex_app.ui.alerts

import android.content.Context
import java.util.concurrent.TimeUnit
import androidx.work.*

object VentilationScheduler {

    fun schedule(context: Context, intervalHours: Int) {

        // 🔥 TEST MODE: run immediately once
        val workRequest = OneTimeWorkRequestBuilder<VentilationWorker>()
            .setInitialDelay(2, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "ventilation_test_run",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }
}