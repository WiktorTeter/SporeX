package com.example.sporex_app.ui.device


import android.content.Context

class DeviceRepository(context: Context) {

    private val prefs = context.getSharedPreferences("device_prefs", Context.MODE_PRIVATE)

    fun getDeviceName(): String =
        prefs.getString("device_name", "AIREX 400") ?: "AIREX 400"

    fun setDeviceName(name: String) {
        prefs.edit().putString("device_name", name).apply()
    }


}
