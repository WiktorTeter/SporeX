package com.example.sporex_app.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.sporex_app.ui.components.UploadActivity
import com.example.sporex_app.ui.navigation.BottomNavBar

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val context = LocalContext.current

    Scaffold(
        bottomBar = { BottomNavBar(currentScreen = "home") }
    ) { paddingValues ->
        HomeScreen(
            modifier = Modifier.padding(paddingValues),
            onUploadClick = {
                context.startActivity(
                    Intent(context, UploadActivity::class.java)
                )
            }
        )
    }
}