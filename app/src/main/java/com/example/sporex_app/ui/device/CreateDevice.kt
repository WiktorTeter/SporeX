package com.example.sporex_app.ui.device

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.sporex_app.ui.navigation.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDeviceScreen(onCreateClick: (String) -> Unit) {

    var deviceName by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        bottomBar = { BottomNavBar(currentScreen = "device") },
        containerColor = Color(0xFF06A546)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Add a New Device", color = Color.White)

            Spacer(Modifier.height(20.dp))

            TextField(
                value = deviceName,
                onValueChange = { deviceName = it },
                label = { Text("Device Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = { onCreateClick(deviceName.text) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Add Device")
            }
        }
    }
}
