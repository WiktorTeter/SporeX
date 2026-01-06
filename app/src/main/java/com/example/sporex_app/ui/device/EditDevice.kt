package com.example.sporex_app.ui.device

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sporex_app.ui.navigation.BottomNavBar
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


private enum class DeviceActionDialog {
    TEST_CONNECTION,
    RESET_DEVICE,
    REMOVE_DEVICE
}


@Composable
fun EditDeviceScreen(onBackClick: () -> Unit) {

    var activeDialog by remember { mutableStateOf<DeviceActionDialog?>(null) }

    Scaffold(
        bottomBar = { BottomNavBar(currentScreen = "device") },
        containerColor = Color(0xFF06A546)
    ) { padding ->

        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Edit Device", color = Color.White)

            Spacer(Modifier.height(20.dp))

            SettingItem("Test Connection") { }
            Spacer(Modifier.height(12.dp))
            SettingItem("Reset Device") { }
            Spacer(Modifier.height(12.dp))
            SettingItem("Remove Device") { }
        }
    }
}

@Composable
fun SettingItem(label: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.85f), RoundedCornerShape(10.dp))
            .padding(20.dp)
            .clickable(onClick = onClick)
    ) {
        Text(label, color = Color.White, modifier = Modifier.weight(1f))
        Text(">", color = Color.White)
    }
}
