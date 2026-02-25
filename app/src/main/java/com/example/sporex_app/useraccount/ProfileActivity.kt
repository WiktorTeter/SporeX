//package com.example.sporex_app.ui.useraccount
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.ui.res.colorResource
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import com.example.sporex_app.R
//import com.example.sporex_app.ui.navigation.BottomNavBar
//import com.example.sporex_app.ui.theme.SPOREX_AppTheme
//
//
//class ProfileActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContent {
//            SPOREX_AppTheme {
//                ProfileScreen(
//                    onHistoryClick = { showToast("History coming soon") },
//
//                    // ✅ SAFEST FIX: MyPostsActivity not available yet, so just show a toast
//                    onPostsClick = { showToast("My Posts coming soon") },
//
//                    onDeviceClick = { showToast("My Device coming soon") },
//                    onSettingsClick = {
//                        startActivity(Intent(this, UserSettings::class.java))
//                    }
//                )
//            }
//        }
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProfileScreen(
//    onHistoryClick: () -> Unit,
//    onPostsClick: () -> Unit,
//    onDeviceClick: () -> Unit,
//    onSettingsClick: () -> Unit
//) {
//    Scaffold(
//        bottomBar = { BottomNavBar(currentScreen = "profile") },
//        containerColor = Color(0xFF08A045)
//    ) { padding ->
//        ProfileContent(
//            modifier = Modifier.padding(padding),
//            onHistoryClick = onHistoryClick,
//            onPostsClick = onPostsClick,
//            onDeviceClick = onDeviceClick,
//            onSettingsClick = onSettingsClick
//        )
//    }
//}
//
//@Composable
//private fun ProfileContent(
//    modifier: Modifier = Modifier,
//    onHistoryClick: () -> Unit,
//    onPostsClick: () -> Unit,
//    onDeviceClick: () -> Unit,
//    onSettingsClick: () -> Unit
//) {
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(horizontal = 24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//
//        // Header background
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .background(colorResource(id = R.color.sporex_green))
//        )
//
//        // Profile picture
//        Box(
//            modifier = Modifier
//                .offset(y = (-60).dp)
//                .size(120.dp)
//                .clip(CircleShape)
//                .background(Color.White),
//            contentAlignment = Alignment.Center
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.chloekim),
//                contentDescription = "Profile Picture",
//                modifier = Modifier
//                    .fillMaxSize()
//                    .clip(CircleShape)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Text(
//            text = "Chloe Kim",
//            style = MaterialTheme.typography.titleMedium,
//            color = Color.White
//        )
//
//        Spacer(modifier = Modifier.height(30.dp))
//
//        ActionButton("My Posts", onPostsClick)
//        ActionButton("History", onHistoryClick)
//        ActionButton("My Device", onDeviceClick)
//        ActionButton("Settings", onSettingsClick)
//    }
//}
//
//@Composable
//private fun ActionButton(text: String, onClick: () -> Unit) {
//    Button(
//        onClick = onClick,
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(54.dp)
//            .padding(vertical = 8.dp),
//        colors = ButtonDefaults.buttonColors(
//            containerColor = Color(0xFFDDDDDD),
//            contentColor = Color.Black
//        )
//    ) {
//        Text(text)
//    }
//}
package com.example.sporex_app.useraccount

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.sporex_app.R
import com.example.sporex_app.ui.navigation.BottomNavBar
import com.example.sporex_app.ui.theme.SPOREX_AppTheme
import com.example.sporex_app.ui.community.CommunityHP
import com.example.sporex_app.ui.components.HistoryActivity
import com.example.sporex_app.ui.device.DeviceActivity
import com.example.sporex_app.ui.navigation.TopBar


class ProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val username = intent.getStringExtra("username") ?: "User"

        setContent {
            SPOREX_AppTheme {
                ProfileScreen(
                    username = username,
                    onHistoryClick = { startActivity(Intent(this, HistoryActivity::class.java)) },
                    onPostsClick = { startActivity(Intent(this, CommunityHP::class.java)) },
                    onDeviceClick = { startActivity(Intent(this, DeviceActivity::class.java)) },
                    onSettingsClick = { startActivity(Intent(this, UserSettings::class.java)) }
                )
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ProfileScreen(
    username: String,
    onHistoryClick: () -> Unit,
    onPostsClick: () -> Unit,
    onDeviceClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Scaffold(
        bottomBar = { BottomNavBar(currentScreen = "profile") },
        containerColor = Color.Transparent
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.sporex_green))
                .padding(
                    bottom = padding.calculateBottomPadding(),
                    start = padding.calculateStartPadding(LayoutDirection.Ltr),
                    end = padding.calculateEndPadding(LayoutDirection.Ltr)
                )
        ) {

            Column(modifier = Modifier.fillMaxSize()) {

                TopBar()

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    color = Color.Transparent
                ) {
                    ProfileContent(
                        username = username,
                        onHistoryClick = onHistoryClick,
                        onPostsClick = onPostsClick,
                        onDeviceClick = onDeviceClick,
                        onSettingsClick = onSettingsClick
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileContent(
    username: String,
    onHistoryClick: () -> Unit,
    onPostsClick: () -> Unit,
    onDeviceClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(170.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
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

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = username,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileActionCard("My Posts", Icons.Default.Article, onPostsClick)
                ProfileActionCard("History", Icons.Default.History, onHistoryClick)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileActionCard("My Device", Icons.Default.Devices, onDeviceClick)
                ProfileActionCard("Settings", Icons.Default.Settings, onSettingsClick)
            }
        }

    }
}



@Composable
private fun ProfileActionCard(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .height(160.dp)
            .width(160.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFF2F2F2),
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(56.dp),
                tint = Color(0xFF08A045)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                label,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )}
    }
}