package com.example.sporex_app.ui.components

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.LayoutDirection
import com.example.sporex_app.ui.navigation.BottomNavBar
import com.example.sporex_app.ui.navigation.TopBar


class UploadActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = this@UploadActivity

            Scaffold(
                bottomBar = { BottomNavBar(currentScreen = "camera") },
                containerColor = Color.White
            ) { paddingValues ->

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF06A546)) // FULL SCREEN GREEN
                        .padding(
                            bottom = paddingValues.calculateBottomPadding(),
                            start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                            end = paddingValues.calculateEndPadding(LayoutDirection.Ltr)
                        )
                ) {

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TopBar()

                        // UploadScreen sits inside the green background
                        UploadScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            onBack = { finish() },
                            onNext = { uri ->
                                val intent = Intent(context, ConfirmationActivity::class.java)
                                intent.putExtra("imageUri", uri.toString())
                                startActivity(intent)
                            }
                        )
                    }
                }
            }
        }
    }
}
            @Composable
            fun UploadScreen(
                modifier: Modifier = Modifier,
                onBack: () -> Unit,
                onNext: (Uri) -> Unit
            ) {
                var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                ) { uri ->
                    selectedImageUri = uri
                }

                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(Color(0xFF06A546))
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Back button (clear label for accessibility)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        TextButton(
                            onClick = onBack
                        ) {
                            Text(
                                text = "← Back",
                                color = Color.Black
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "Upload Mould Image",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "Tap the box below to choose a photo from your device. Make sure the mould is clearly visible.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black.copy(alpha = 0.85f)
                    )

                    Spacer(Modifier.height(28.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        onClick = { launcher.launch("image/*") }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (selectedImageUri == null) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Tap to upload a photo",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Black
                                    )
                                    Spacer(Modifier.height(6.dp))
                                    Text(
                                        text = "Supported formats: JPG or PNG",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                }
                            } else {
                                Image(
                                    painter = rememberAsyncImagePainter(selectedImageUri),
                                    contentDescription = "Selected image of mould for analysis",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    // Helper text after selection
                    if (selectedImageUri != null) {
                        Text(
                            text = "Image selected. Press Continue to proceed.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    }

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = { selectedImageUri?.let(onNext) },
                        enabled = selectedImageUri != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            disabledContainerColor = Color.Black.copy(alpha = 0.4f)
                        )
                    ) {
                        Text(
                            text = "Continue",
                            color = Color.White
                        )
                    }
                }
            }



