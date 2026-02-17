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
import com.example.sporex_app.ui.useraccount.SettingsStore

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = this
            var isDarkMode by remember {mutableStateOf(SettingsStore.getDarkMode(context)) }

            SPOREX_AppTheme (darkTheme = isDarkMode)
            {

                Scaffold(
                    bottomBar = { BottomNavBar() }
                ) { padding ->
                    MainScreen(
                        paddingValues = padding,

                        onThemeChange = { enabled ->

                            isDarkMode = enabled

                            SettingsStore.setDarkMode(context, enabled)

                            },
                        isDarkMode = isDarkMode
                    )
                }
            }
        }
    }
}

private fun MainActivity.BottomNavBar() {
    TODO("Not yet implemented")
}
