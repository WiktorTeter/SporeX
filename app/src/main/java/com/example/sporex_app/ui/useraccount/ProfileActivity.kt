package com.example.sporex_app.ui.useraccount

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.res.colorResource
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sporex_app.R
import com.example.sporex_app.ui.navigation.BottomNavBar
import com.example.sporex_app.ui.theme.SPOREX_AppTheme


class ProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SPOREX_AppTheme {
                ProfileScreen(
                    onHistoryClick = { showToast("History coming soon") },

                    // ✅ SAFEST FIX: MyPostsActivity not available yet, so just show a toast
                    onPostsClick = { showToast("My Posts coming soon") },

                    onDeviceClick = { showToast("My Device coming soon") },
                    onSettingsClick = {
                        startActivity(Intent(this, UserSettings::class.java))
                    }
                )
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onHistoryClick: () -> Unit,
    onPostsClick: () -> Unit,
    onDeviceClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Scaffold(
        bottomBar = { BottomNavBar(currentScreen = "profile") },
        containerColor = Color(0xFF08A045)
    ) { padding ->
        ProfileContent(
            modifier = Modifier.padding(padding),
            onHistoryClick = onHistoryClick,
            onPostsClick = onPostsClick,
            onDeviceClick = onDeviceClick,
            onSettingsClick = onSettingsClick
        )
    }
}

@Composable
private fun ProfileContent(
    modifier: Modifier = Modifier,
    onHistoryClick: () -> Unit,
    onPostsClick: () -> Unit,
    onDeviceClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Header background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(colorResource(id = R.color.sporex_green))
        )

        // Profile picture
        Box(
            modifier = Modifier
                .offset(y = (-60).dp)
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.chloekim),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Chloe Kim",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(30.dp))

        ActionButton("My Posts", onPostsClick)
        ActionButton("History", onHistoryClick)
        ActionButton("My Device", onDeviceClick)
        ActionButton("Settings", onSettingsClick)
    }
}

@Composable
private fun ActionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFDDDDDD),
            contentColor = Color.Black
        )
    ) {
        Text(text)
    }
}
