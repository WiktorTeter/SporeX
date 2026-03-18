package com.example.sporex_app.ui.onboarding

import android.content.Context
import com.example.sporex_app.MainActivity

class OnboardingPageOne (context: Context){

    private val prefs = context.getSharedPreferences("sporex_prefs", Context.MODE_PRIVATE)

    fun isFirstLaunch() : Boolean{
        return prefs.getBoolean("first_launch", true)
    }

    fun finishOnboarding(){
        prefs.edit().putBoolean("first_launch", false).apply()
    }
}

annotation class OnboardingManager(val activity: MainActivity) {
    fun isFirstLaunch() {
        TODO("Not yet implemented")

    }

    fun finishOnboarding() {
        TODO("Not yet implemented")
    }
}
