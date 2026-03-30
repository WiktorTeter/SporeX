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
import com.example.sporex_app.ui.theme.SPOREX_AppTheme
import com.example.sporex_app.ui.onboarding.OnboardingPageOne


import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.sporex_app.ui.components.UploadActivity
import com.example.sporex_app.ui.components.ProductsActivity
import com.example.sporex_app.ui.components.HistoryActivity
import com.example.sporex_app.ui.screens.HomeScreen
import com.example.sporex_app.utils.isDarkMode
import com.example.sporex_app.utils.setDarkMode

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val onboardingManager = OnboardingPageOne(this)

        setContent {
            val isFirstLaunch = onboardingManager.isFirstLaunch()
            HomeScreen(
                showOnboardingInitially = isFirstLaunch,
                onOnboardingFinished = {
                    onboardingManager.finishOnboarding()

                }

                )
        
        
            var isDarkMode by remember {mutableStateOf(false)}

            SPOREX_AppTheme (darkTheme = isDarkMode)
            {
            val context = LocalContext.current
            var isDarkMode by remember { mutableStateOf(isDarkMode(context)) } // ← read from prefs

            SPOREX_AppTheme(darkTheme = isDarkMode) {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background,
                    bottomBar = { BottomNavBar(currentScreen = "home") }
                ) {
                    HomeScreen(
                        modifier = Modifier,
                        onUploadClick = {
                            context.startActivity(Intent(context, UploadActivity::class.java))
                        },
                        onProductsClick = {
                            context.startActivity(Intent(context, ProductsActivity::class.java))
                        },
                        onHistoryClick = {
                            context.startActivity(Intent(context, HistoryActivity::class.java))
                        }
                    )
                }
            }
        }
    }


private fun MainActivity.HomeScreen(
    showOnboardingInitially: Any,
    onOnboardingFinished: () -> Unit
) {

}
}
