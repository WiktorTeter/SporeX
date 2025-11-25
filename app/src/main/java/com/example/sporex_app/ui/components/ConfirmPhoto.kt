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
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.layout.ContentScale
import com.example.sporex_app.ui.navigation.BottomNavBar

class ConfirmationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageUri = intent.getStringExtra("imageUri")

        setContent {
            Scaffold(
                bottomBar = { BottomNavBar(currentScreen = "camera") }
            ) { paddingValues ->

                ConfirmationScreen(
                    imageUri = imageUri,
                    onNext = {
                        startActivity(Intent(this, ResultActivity::class.java))
                    },
                    onBack = { finish() }
                )
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
                .background(Color(0xFF06A546))
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(Color.Black, Color.White)
            ) {
                Text("Back")
            }

            Spacer(modifier = Modifier.height(20.dp))

            uri?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = null,
                    modifier = Modifier
                        .size(300.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Are you sure you want to use this photo?")


            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onNext,
                colors = ButtonDefaults.buttonColors(Color.Black, Color.White)
            ) {
                Text("Next")
            }
        }
    }
}
