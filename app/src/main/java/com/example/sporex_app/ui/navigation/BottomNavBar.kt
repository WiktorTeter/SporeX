package com.example.sporex_app.ui.navigation

import android.content.Intent
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.example.sporex_app.MainActivity
import com.example.sporex_app.R
import com.example.sporex_app.ui.components.UploadActivity
import com.example.sporex_app.ui.device.DeviceActivity
import com.example.sporex_app.ui.useraccount.ProfileActivity

@Composable
fun BottomNavBar(currentScreen: String) {
    val context = LocalContext.current

    val items = listOf(
        NavItem.Home,
        NavItem.Devices,
        NavItem.Camera,
        NavItem.Profile
    )

    NavigationBar(containerColor = colorResource(id = R.color.sporex_black)) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentScreen == item.route,
                onClick = {
                    when (item.route) {
                        "home" -> context.startActivity(Intent(context, MainActivity::class.java))
                        "devices" -> context.startActivity(Intent(context, DeviceActivity::class.java))
                        "camera" -> context.startActivity(Intent(context, UploadActivity::class.java))  // Add action for camera
                        "profile" -> context.startActivity(Intent(context, ProfileActivity::class.java))
                    }
                },
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.route,
                        tint = if (currentScreen == item.route)
                            colorResource(id = R.color.sporex_green)
                        else
                            colorResource(id = R.color.sporex_green)
                    )
                }
            )
        }
    }
}
