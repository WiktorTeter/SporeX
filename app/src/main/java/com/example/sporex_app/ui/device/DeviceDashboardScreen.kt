package com.example.sporex_app.ui.device

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sporex_app.ui.navigation.BottomNavBar

@Composable
fun DeviceDashboardScreen(
    deviceName: String = "AIREX 400",
    onManageDeviceClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = { BottomNavBar(currentScreen = "device") },
        containerColor = Color(0xFF08A045) // Your green background
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = deviceName,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            Text(
                text = "Online",
                color = Color(0xFF06FF4B),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(16.dp))

            Card(
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF3A3A3A) // Dark card — readable text
                )
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Basic Readings",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )

                    if (expanded) {
                        Spacer(Modifier.height(12.dp))
                        StatRow("AIR QUALITY", "2", "Good")
                        StatRow("Humidity", "91%")
                        StatRow("CO₂", "2.77 ppm")
                        StatRow("% Chance of Mould", "less than 10%")
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onManageDeviceClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Manage Device")
            }
        }
    }
}

@Composable
fun StatRow(label: String, value: String, desc: String? = null) {
    Column(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
        desc?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = Color.LightGray
            )
        }
    }
}

