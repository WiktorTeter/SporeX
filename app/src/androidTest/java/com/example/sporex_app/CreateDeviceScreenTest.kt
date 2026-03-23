package com.example.sporex_app

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.sporex_app.ui.device.CreateDeviceScreen
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CreateDeviceScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun scanForDevices_showsMockDevices() {
        composeRule.setContent {
            CreateDeviceScreen(onCreateClick = {})
        }

        composeRule.onNodeWithText("Scan for Devices").assertExists().performClick()

        composeRule.onNodeWithText("Sporex Sensor A").assertExists()
        composeRule.onNodeWithText("Sporex Sensor B").assertExists()
        composeRule.onNodeWithText("Arduino Device").assertExists()
    }

    @Test
    fun selectingDevice_callsCallback_withChosenDeviceName() {
        var selected = ""

        composeRule.setContent {
            CreateDeviceScreen(onCreateClick = { selected = it })
        }

        composeRule.onNodeWithText("Scan for Devices").performClick()
        composeRule.onNodeWithText("Sporex Sensor A").performClick()

        assertEquals("Sporex Sensor A", selected)
    }
}