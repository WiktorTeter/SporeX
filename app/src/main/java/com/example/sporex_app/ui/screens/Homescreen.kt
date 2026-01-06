package com.example.sporex_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sporex_app.R
import com.example.sporex_app.ui.navigation.BottomNavBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Notifications

@Composable
private fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(Modifier.width(24.dp))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "SporeX Logo",
            modifier = Modifier.height(32.dp)
        )

        Icon(
            imageVector = Icons.Filled.Notifications,
            contentDescription = "Notifications",
            tint = Color(0xFF06A546),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun PreviousCaseCard() {
    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = "Previous Case",
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "65%",
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "SPOREX has detected 65% exposure of Trichoderma in your home.",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Click for more information",
                    color = Color(0xFF06A546),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF06A546), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "View Case",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun CameraCard(onUploadClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {

        Card(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                modifier = Modifier
                    .height(180.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = "Camera",
                    tint = Color(0xFF06A546),
                    modifier = Modifier.size(64.dp)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onUploadClick,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Text("Quick Scan", color = Color.White)
        }
    }
}


@Composable
fun HomeScreen(
    onUploadClick: () -> Unit
) {
    Scaffold(
        bottomBar = { BottomNavBar(currentScreen = "home") },
        containerColor = Color(0xFF06A546)
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            TopBar()

            Spacer(Modifier.height(16.dp))

            PreviousCaseCard()

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Welcome",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 20.dp)
            )

            Spacer(Modifier.height(16.dp))

            CameraCard(onUploadClick)
        }
    }
}
