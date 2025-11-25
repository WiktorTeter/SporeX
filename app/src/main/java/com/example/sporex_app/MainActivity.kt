package com.example.sporex_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.sporex_app.ui.theme.SPOREX_AppTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.sporex_app.ui.screens.MainScreen
import androidx.compose.material3.Scaffold
import com.example.sporex_app.ui.navigation.BottomNavBar


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        //splash install :3
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SPOREX_AppTheme {
                Scaffold(
                    bottomBar = { BottomNavBar(currentScreen = "home") }
                ) { padding ->
                    MainScreen()
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        SPOREX_AppTheme {
        }
    }
}
