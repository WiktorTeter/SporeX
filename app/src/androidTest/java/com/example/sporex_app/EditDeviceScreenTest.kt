package com.example.sporex_app

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.sporex_app.ui.device.EditDeviceScreen
import org.junit.Rule
import org.junit.Test

class EditDeviceScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun editDevice_showsAllSettingItems_andTheyAreClickable() {
        composeRule.setContent {
            EditDeviceScreen(onBackClick = {})
        }

        composeRule.onNodeWithText("Edit Device").assertExists()

        composeRule.onNodeWithText("Test Connection").assertExists().assertHasClickAction()
        composeRule.onNodeWithText("Reset Device").assertExists().assertHasClickAction()
        composeRule.onNodeWithText("Remove Device").assertExists().assertHasClickAction()
    }
}
