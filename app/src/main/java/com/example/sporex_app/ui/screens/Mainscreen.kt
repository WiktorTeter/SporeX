package com.example.sporex_app.ui.screens

import android.content.Intent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.sporex_app.ui.components.UploadActivity
import com.example.sporex_app.ui.navigation.BottomNavBar
import androidx.compose.ui.platform.LocalContext

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val context = LocalContext.current

    Scaffold(
        bottomBar = { BottomNavBar(currentScreen = "home") }
    ) { paddingValues ->
        HomeScreen(
            onUploadClick = {
                context.startActivity(
                    Intent(context, UploadActivity::class.java)
                )
            }
        )
    }
}