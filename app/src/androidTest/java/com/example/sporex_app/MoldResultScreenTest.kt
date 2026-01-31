package com.example.sporex_app

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.sporex_app.ui.components.MoldResultScreen
import com.example.sporex_app.ui.theme.SPOREX_AppTheme
import org.junit.Rule
import org.junit.Test

class MoldResultScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun viewDetails_opensBottomSheet_and_mockTest_opensAndClosesDialog() {
        composeRule.setContent {
            SPOREX_AppTheme(dynamicColor = false) {
                MoldResultScreen()
            }
        }

        // Open bottom sheet
        composeRule.onNodeWithText("View Details").assertExists().performClick()
        composeRule.onNodeWithText("Cladosporium Details").assertExists()

        // Open dialog
        composeRule.onNodeWithText("Run Mock Air Quality Test").assertExists().performClick()
        composeRule.onNodeWithText("Mock Air Quality Test").assertExists()

        // Close dialog
        composeRule.onNodeWithText("Close").assertExists().performClick()
        composeRule.onNodeWithText("Mock Air Quality Test").assertDoesNotExist()
    }
}
