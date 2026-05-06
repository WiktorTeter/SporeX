package com.example.sporex_app

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sporex_app.ui.device.DeviceRepository
import com.example.sporex_app.ui.device.EditDeviceScreen
import com.example.sporex_app.ui.theme.SPOREX_AppTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditDeviceScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private fun createRepo(): DeviceRepository {
        val context = ApplicationProvider.getApplicationContext<Context>()
        return DeviceRepository(context)
    }

    @Test
    fun deviceName_isDisplayed() {
        val repo = createRepo()

        composeRule.setContent {
            SPOREX_AppTheme {
                EditDeviceScreen(
                    deviceName = "Test Device",
                    onRename = {},
                    onBackClick = {},
                    repo = repo,
                    onDeviceDetailsClick = {},
                    onTestConnectionClick = {}
                )
            }
        }

        composeRule.onNodeWithText("Test Device").assertIsDisplayed()
        composeRule.onNodeWithText("Online").assertIsDisplayed()
    }

    @Test
    fun settingsOptions_areDisplayedWithCorrectText() {
        val repo = createRepo()

        composeRule.setContent {
            SPOREX_AppTheme {
                EditDeviceScreen(
                    deviceName = "Test Device",
                    onRename = {},
                    onBackClick = {},
                    repo = repo,
                    onDeviceDetailsClick = {},
                    onTestConnectionClick = {}
                )
            }
        }

        composeRule.onNodeWithText("Edit Device Name").assertExists()
        composeRule.onNodeWithText("Device Details").assertExists()
        composeRule.onNodeWithText("Test Connection").assertExists()
    }

    @Test
    fun actionButtons_areDisplayedWithCorrectText() {
        val repo = createRepo()

        composeRule.setContent {
            SPOREX_AppTheme {
                EditDeviceScreen(
                    deviceName = "Test Device",
                    onRename = {},
                    onBackClick = {},
                    repo = repo,
                    onDeviceDetailsClick = {},
                    onTestConnectionClick = {}
                )
            }
        }

        composeRule.onNodeWithText("Reset Device").assertExists()
        composeRule.onNodeWithText("Remove Device").assertExists()
    }

    @Test
    fun clickingDeviceDetails_callsCallback() {
        val repo = createRepo()
        var clicked = false

        composeRule.setContent {
            SPOREX_AppTheme {
                EditDeviceScreen(
                    deviceName = "Test Device",
                    onRename = {},
                    onBackClick = {},
                    repo = repo,
                    onDeviceDetailsClick = {
                        clicked = true
                    },
                    onTestConnectionClick = {}
                )
            }
        }

        composeRule.onNodeWithText("Device Details").performClick()

        assertTrue(clicked)
    }

    @Test
    fun clickingTestConnection_callsCallback() {
        val repo = createRepo()
        var clicked = false

        composeRule.setContent {
            SPOREX_AppTheme {
                EditDeviceScreen(
                    deviceName = "Test Device",
                    onRename = {},
                    onBackClick = {},
                    repo = repo,
                    onDeviceDetailsClick = {},
                    onTestConnectionClick = {
                        clicked = true
                    }
                )
            }
        }

        composeRule.onNodeWithText("Test Connection").performClick()

        assertTrue(clicked)
    }

    @Test
    fun clickingRemoveDevice_callsBackCallback() {
        val repo = createRepo()
        var backClicked = false

        repo.setDeviceId("test-device-id")

        composeRule.setContent {
            SPOREX_AppTheme {
                EditDeviceScreen(
                    deviceName = "Test Device",
                    onRename = {},
                    onBackClick = {
                        backClicked = true
                    },
                    repo = repo,
                    onDeviceDetailsClick = {},
                    onTestConnectionClick = {}
                )
            }
        }

        composeRule.onNodeWithText("Remove Device").performClick()

        assertTrue(backClicked)
    }

    @Test
    fun clickingEditDeviceName_opensRenameDialog() {
        val repo = createRepo()

        composeRule.setContent {
            SPOREX_AppTheme {
                EditDeviceScreen(
                    deviceName = "Test Device",
                    onRename = {},
                    onBackClick = {},
                    repo = repo,
                    onDeviceDetailsClick = {},
                    onTestConnectionClick = {}
                )
            }
        }

        composeRule.onNodeWithText("Edit Device Name").performClick()

        composeRule.onNodeWithText("Save").assertExists()
        composeRule.onNodeWithText("Cancel").assertExists()
    }

//    @Test
//    fun renameDialog_savesNewDeviceName() {
//        val repo = createRepo()
//
//        var renamedValue = ""
//        var backClicked = false
//
//        composeRule.setContent {
//            SPOREX_AppTheme {
//                EditDeviceScreen(
//                    deviceName = "Test Device",
//                    onRename = { newName ->
//                        renamedValue = newName
//                    },
//                    onBackClick = {
//                        backClicked = true
//                    },
//                    repo = repo,
//                    onDeviceDetailsClick = {},
//                    onTestConnectionClick = {}
//                )
//            }
//        }
//
//        composeRule.onNodeWithText("Edit Device Name").performClick()
//
//        composeRule.onNodeWithText("Test Device")
//            .performTextClearance()
//
//        composeRule.onNodeWithText("")
//            .performTextInput("Updated Device")
//
//        composeRule.onNodeWithText("Save").performClick()
//
//        assertEquals("Updated Device", renamedValue)
//        assertTrue(backClicked)
//    }
}