package com.example.sporex_app.ui.device


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sporex_app.ui.navigation.BottomNavBar
import com.example.sporex_app.ui.navigation.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDeviceScreen(
    onCreateClick: (String) -> Unit
) {
    var isScanning by remember { mutableStateOf(false) }

    // Temporary mock device list (replace with real Bluetooth scan results later)
    var devices by remember {
        mutableStateOf(listOf<String>())
    }


    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavBar(currentScreen = "device") },
        containerColor = Color(0xFF06A546)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Connect a Device",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Turn on your device and tap scan to find it.",
                color = Color.White.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    isScanning = true

                    // Mock scan results (replace with real Bluetooth scan)
                    devices = listOf(
                        "Sporex Sensor A",
                        "Sporex Sensor B",
                        "Arduino Device"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(if (isScanning) "Scanning..." else "Scan for Devices")
            }

            Spacer(Modifier.height(24.dp))

            if (devices.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(devices) { device ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable {
                                    // User selects device → create it
                                    onCreateClick(device)
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF3A3A3A)
                            )
                        ) {
                            Text(
                                text = device,
                                modifier = Modifier.padding(16.dp),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
