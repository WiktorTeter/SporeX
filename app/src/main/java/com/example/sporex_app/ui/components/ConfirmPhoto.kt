package com.example.sporex_app.ui.components

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.sporex_app.ui.navigation.BottomNavBar
import com.example.sporex_app.ui.navigation.TopBar


class ConfirmationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageUri = intent.getStringExtra("imageUri")
        val activity = this@ConfirmationActivity

        setContent {
            Scaffold(
                bottomBar = { BottomNavBar(currentScreen = "camera") },
                containerColor = Color.White
            ) { padding ->

                val bottomPadding = padding.calculateBottomPadding()

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF06A546))
                        .padding(bottom = bottomPadding)
                ) {

                    Column(modifier = Modifier.fillMaxSize()) {

                        // TopBar sits flush at the top now
                        TopBar()

                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp)
                        ) {

                            ConfirmationScreen(
                                imageUri = imageUri,
                                onNext = {
                                    activity.startActivity(
                                        Intent(activity, ResultActivity::class.java)
                                    )
                                },
                                onBack = { activity.finish() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConfirmationScreen(
    imageUri: String?,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val uri = imageUri?.let { Uri.parse(it) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Review Your Photo",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )

        Spacer(Modifier.height(20.dp))

        uri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Uploaded mould photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Is this the correct photo for analysis?",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )

        Spacer(Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                onClick = onBack,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                )
            ) {
                Text("Retake")
            }

            Spacer(Modifier.width(16.dp))

            Button(
                onClick = onNext,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Confirm")
            }
        }
    }
}