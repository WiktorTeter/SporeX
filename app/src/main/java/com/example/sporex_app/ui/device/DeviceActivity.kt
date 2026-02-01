package com.example.sporex_app.ui.device

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sporex_app.ui.theme.SPOREX_AppTheme

class DeviceActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SPOREX_AppTheme {

                val navController: NavHostController = rememberNavController()
                val snackBarState = remember { SnackbarHostState() }

                NavHost(
                    navController = navController,
                    startDestination = DeviceScreen.DeviceDashboard.route
                ) {
                    composable(DeviceScreen.DeviceSetup.route) {
                        CreateDeviceScreen(
                            onCreateClick = {
                                navController.navigate(DeviceScreen.DeviceDashboard.route)
                            }
                        )
                    }
                    composable(DeviceScreen.DeviceDashboard.route) {
                        DeviceDashboardScreen(
                            onManageDeviceClick = {
                                navController.navigate(DeviceScreen.DeviceEdit.route)
                            }
                        )
                    }
                    composable(DeviceScreen.DeviceEdit.route) {
                        EditDeviceScreen(
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
