package com.example.sporex_app

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.hasSetTextAction
import com.example.sporex_app.ui.device.CreateDeviceScreen
import com.example.sporex_app.ui.theme.SPOREX_AppTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CreateDeviceScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun addDevice_passesEnteredName_toCallback() {
        var captured = ""

        composeRule.setContent {
            SPOREX_AppTheme(dynamicColor = false) {
                CreateDeviceScreen(onCreateClick = { captured = it })
            }
        }

        // There is only one TextField, so grab the first node that supports text input
        composeRule.onAllNodes(hasSetTextAction())[0].performTextInput("AIREX 400")

        composeRule.onNodeWithText("Add Device").assertExists().performClick()

        assertEquals("AIREX 400", captured)
    }
}
