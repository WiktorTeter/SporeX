package com.example.sporex_app.ui.device

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

                // Shared repository for all screens
                val repo = DeviceRepository(this)

                NavHost(
                    navController = navController,
                    startDestination = DeviceScreen.DeviceDashboard.route
                ) {

                    // CREATE DEVICE SCREEN
                    composable(DeviceScreen.DeviceSetup.route) {
                        CreateDeviceScreen(
                            onCreateClick = { name ->
                                repo.setDeviceName(name)
                                navController.navigate(DeviceScreen.DeviceDashboard.route)
                            }
                        )
                    }


                    // DASHBOARD SCREEN
                    composable(DeviceScreen.DeviceDashboard.route) {
                        DeviceDashboardScreen(
                            deviceName = repo.getDeviceName(),
                            onManageDeviceClick = {
                                navController.navigate(DeviceScreen.DeviceEdit.route)
                            }
                        )
                    }


                    // EDIT DEVICE SCREEN
                    composable(DeviceScreen.DeviceEdit.route) {
                        EditDeviceScreen(
                            deviceName = repo.getDeviceName(),
                            onRename = { newName ->
                                repo.setDeviceName(newName)
                            },
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )

                    }
                }
            }
        }
    }
}