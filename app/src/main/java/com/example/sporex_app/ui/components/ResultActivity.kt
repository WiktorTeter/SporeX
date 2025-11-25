package com.example.sporex_app.ui.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.sporex_app.ui.navigation.BottomNavBar

class ResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Scaffold(
                bottomBar = { BottomNavBar(currentScreen = "camera") }
            ) { paddingValues ->
                MoldResultScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoldResultScreen() {

    var showDetails by remember { mutableStateOf(false) }
    var showMockTest by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF06A546))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "SPOREX Analysis Complete",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Your photo shows an estimated **65% likelihood** of *Cladosporium* mold.",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { showDetails = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )

            ) {
                Text("View Details")
            }
        }
    }

    // ---------- MODAL BOTTOM SHEET ----------
    if (showDetails) {
        ModalBottomSheet(
            onDismissRequest = { showDetails = false }
        ) {
            Column(
                modifier = Modifier
                    .background(Color(0xFF06A546))
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {

                Text(
                    text = "Cladosporium Details",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    "• Common indoor mold often found on walls, ceilings, and damp surfaces.\n" +
                            "• Can cause allergies, coughing, and irritation in sensitive individuals.\n" +
                            "• Thrives in humid or poorly ventilated areas.\n" +
                            "• Recommended action: Improve ventilation, clean affected areas, and monitor for growth."
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Risk Assessment",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("• Exposure Risk: Moderate")
                Text("• Growth Rate: Medium")
                Text("• Surface Penetration: Low")
                Text("• Recommended Testing: Air Quality Test")

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { showMockTest = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("Run Mock Air Quality Test")
                }


                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // ---------- MOCK TEST POPUP ----------
    if (showMockTest) {
        Dialog(onDismissRequest = { showMockTest = false }) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 6.dp,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Mock Air Quality Test",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Simulated airborne spore concentration detected:\n\n" +
                                "• Spore Density: 182 spores/m³\n" +
                                "• Threshold: 150 spores/m³\n" +
                                "• Status: Slightly Elevated\n\n" +
                                "Recommendation: Increase ventilation and consider HEPA filtration."
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(onClick = { showMockTest = false }) {
                        Text("Close")
                    }
                }
            }
        }
    }
}
