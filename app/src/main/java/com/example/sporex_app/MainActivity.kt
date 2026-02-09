package com.example.sporex_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.sporex_app.ui.navigation.BottomNavBar
import com.example.sporex_app.ui.screens.MainScreen
import com.example.sporex_app.ui.theme.SPOREX_AppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SPOREX_AppTheme {
                Scaffold(
                    bottomBar = { BottomNavBar(currentScreen = "home") }
                ) { innerPadding ->
                    MainScreen(paddingValues = innerPadding)
                }
            }
        }
    }
}
