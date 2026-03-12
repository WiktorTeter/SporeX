package com.example.sporex_app.ui.device

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.sporex_app.ui.navigation.BottomNavBar
import com.example.sporex_app.ui.navigation.TopBar

@Composable
fun DeviceDashboardScreen(
    deviceName: String,
    onManageDeviceClick: () -> Unit,
    onCreateDeviceClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = { BottomNavBar(currentScreen = "device") },
        containerColor = Color.White,

        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateDeviceClick,
                containerColor = Color.Black,
                contentColor = Color.White,
                shape = RoundedCornerShape(50),
                modifier = Modifier.size(56.dp)
            ) {
                Text("+", style = MaterialTheme.typography.titleLarge)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF06A546))
                .padding(
                    bottom = padding.calculateBottomPadding(),
                    start = padding.calculateStartPadding(LayoutDirection.Ltr),
                    end = padding.calculateEndPadding(LayoutDirection.Ltr)
                )
        ) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopBar()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
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
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF3A3A3A)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Basic Readings",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White
                                )
                                Icon(
                                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = "Expand",
                                    tint = Color.White
                                )
                            }

                            if (expanded) {
                                Spacer(Modifier.height(12.dp))
                                StatRow(label = "Air Quality", value = "2", desc = "Good")
                                Divider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp)
                                StatRow(label = "Humidity", value = "91%")
                                Divider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp)
                                StatRow(label = "CO₂", value = "2.77 ppm")
                                Divider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp)
                                StatRow(label = "% Chance of Mould", value = "less than 10%")
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
    }
}

@Composable
fun StatRow(label: String, value: String, desc: String? = null) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label.uppercase(),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold),
                color = Color.White // stronger contrast
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Medium),
                color = Color(0xFF06FF4B) // optional: highlight key values like Humidity/CO2
            )
        }

        desc?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Normal),
                color = Color.LightGray,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}


//@Composable
//fun StatRow(label: String, value: String, desc: String? = null) {
//    Column(
//        Modifier
//            .fillMaxWidth()
//            .padding(vertical = 6.dp)
//    ) {
//        Text(
//            text = label.uppercase(),
//            style = MaterialTheme.typography.bodySmall,
//            color = Color.White
//        )
//        Text(
//            text = value,
//            style = MaterialTheme.typography.bodyLarge,
//            color = Color.White
//        )
//        desc?.let {
//            Text(
//                text = it,
//                style = MaterialTheme.typography.bodySmall,
//                color = Color.LightGray
//            )
//        }
//    }
//}
