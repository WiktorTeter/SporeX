package com.example.sporex_app.ui.useraccount

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.sporex_app.R
import com.example.sporex_app.ui.navigation.BottomNavBar
import com.example.sporex_app.ui.navigation.TopBar
import com.example.sporex_app.ui.theme.SPOREX_AppTheme

class UserSettings : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SPOREX_AppTheme {
                UserSettingsScreen()
            }
        }
    }
}

@Composable
fun UserSettingsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.sporex_green))
    ) {
        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomNavBar(currentScreen = "settings") },
            containerColor = Color.Transparent
        ) { padding ->

            Box(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorResource(id = R.color.sporex_black),
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    SettingsOption("Your Account") { }
                    SettingsOption("App Customisation") { }
                    SettingsOption("Data Personalisation") { }
                    SettingsOption("Notifications") { }
                    SettingsOption("Log out") { }
                }
            }
        }
    }
}

@Composable
fun SettingsOption(label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = colorResource(id = R.color.sporex_white),
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Go to $label",
            tint = colorResource(id = R.color.sporex_white)
        )
    }
}

