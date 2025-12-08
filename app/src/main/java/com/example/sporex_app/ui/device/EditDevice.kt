package com.example.sporex_app.ui.device

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sporex_app.ui.navigation.BottomNavBar

@Composable
fun EditDeviceScreen(onBackClick: () -> Unit) {
    Scaffold(
        bottomBar = { BottomNavBar(currentScreen = "device") },
        containerColor = Color(0xFF08A045)
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Edit Device", color = Color.White)
            Spacer(Modifier.height(20.dp))
            Spacer(Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Blue),
                onClick = { }
            ) {
                Text("Reset Device")
            }

            Spacer(Modifier.height(12.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Red),
                onClick = { }
            ) {
                Text("Remove Device")
            }
        }
    }
}

@Composable
fun SettingItem(label: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color.DarkGray, RoundedCornerShape(10.dp))
            .padding(20.dp)
            .clickable(onClick = onClick)
    ) {
        Text(label, color = Color.White, modifier = Modifier.weight(1f))
        Text(">", color = Color.White)
    }
}
