package com.example.sporex_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.sporex_app.ui.navigation.BottomNavBar
import com.example.sporex_app.ui.screens.MainScreen
import com.example.sporex_app.ui.theme.SPOREX_AppTheme
import com.example.sporex_app.ui.onboarding.OnboardingManager
import com.example.sporex_app.ui.screens.HomeScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val onboardingManager = OnboardingManager(this)

        setContent {
            val isFirstLaunch = onboardingManager.isFirstLaunch()
            HomeScreen(
                showOnboardingInitially = isFirstLaunch,
                onOnboardingFinished = {
                    onboardingManager.finishOnboarding()
                })
        
        
            var isDarkMode by remember {mutableStateOf(false)}

            SPOREX_AppTheme (darkTheme = isDarkMode)
            {

                Scaffold(
                    bottomBar = { BottomNavBar(currentScreen = "home") }
                ) { innerPadding ->
                    MainScreen(
                        paddingValues = innerPadding,
                        isDarkMode = isDarkMode,
                        onThemeChange = { isDarkMode = it})
                }
            }
        }
    }
}

private fun MainActivity.HomeScreen(
    showOnboardingInitially: Any,
    onOnboardingFinished: () -> Unit
) {
}
