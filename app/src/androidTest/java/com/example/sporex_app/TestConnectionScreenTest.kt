package com.example.sporex_app

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.sporex_app.ui.device.TestConnectionScreen
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class TestConnectionScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun testConnectionScreen_showsDeviceInfo_whenConnected() {
        composeRule.setContent {
            TestConnectionScreen(
                deviceName = "AIREX 400",
                firmwareVersion = "2.3.1",
                connectionStatus = true,
                onReconnectClick = {}
            )
        }

        composeRule.onNodeWithText("AIREX 400").assertIsDisplayed()
        composeRule.onNodeWithText("Online").assertIsDisplayed()
        composeRule.onNodeWithText("Test Connection").assertIsDisplayed()
        composeRule.onNodeWithText("Your Connection").assertIsDisplayed()
        composeRule.onNodeWithText("Connection Status: Connected ✅").assertIsDisplayed()
        composeRule.onNodeWithText("Device Name: AIREX 400").assertIsDisplayed()
        composeRule.onNodeWithText("Model: AIREX 400").assertIsDisplayed()
        composeRule.onNodeWithText("Firmware Version: 2.3.1").assertIsDisplayed()
    }

    @Test
    fun reconnectButton_callsCallback_once() {
        var clicks = 0

        composeRule.setContent {
            TestConnectionScreen(
                deviceName = "AIREX 400",
                connectionStatus = true,
                onReconnectClick = { clicks++ }
            )
        }

        composeRule.onNodeWithText("Reconnect Device")
            .assertExists()
            .assertHasClickAction()
            .performClick()

        assertEquals(1, clicks)
    }

    @Test
    fun testConnectionScreen_showsDisconnectedState() {
        composeRule.setContent {
            TestConnectionScreen(
                deviceName = "AIREX 400",
                connectionStatus = false,
                onReconnectClick = {}
            )
        }

        composeRule.onNodeWithText("Connection Status: Disconnected ❌").assertIsDisplayed()
    }
}