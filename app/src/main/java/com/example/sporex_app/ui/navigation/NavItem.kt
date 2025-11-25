package com.example.sporex_app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(
    val route: String,
    val icon: ImageVector
) {
    object Home : NavItem("home", Icons.Filled.Home)
//    object Messages : NavItem("device", Icons.Filled.device)
    object Camera : NavItem("camera", Icons.Filled.CameraAlt)
//    object Profile : NavItem("profile", Icons.Filled.Person)
object Profile : NavItem("profile", Icons.Filled.Person)
}