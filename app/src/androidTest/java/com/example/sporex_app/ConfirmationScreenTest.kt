package com.example.sporex_app

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.sporex_app.ui.components.ConfirmationScreen
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ConfirmationScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun confirmationScreen_showsExpectedContent() {
        composeRule.setContent {
            ConfirmationScreen(
                imageUri = "content://test/image.jpg",
                onNext = {},
                onBack = {}
            )
        }

        composeRule.onNodeWithText("Review Your Photo").assertIsDisplayed()
        composeRule.onNodeWithText("Is this the correct photo for analysis?").assertIsDisplayed()
        composeRule.onNodeWithText("Retake").assertExists().assertHasClickAction()
        composeRule.onNodeWithText("Confirm").assertExists().assertHasClickAction()
    }

    @Test
    fun confirmationScreen_buttonsTriggerCallbacks() {
        var backClicks = 0
        var nextClicks = 0

        composeRule.setContent {
            ConfirmationScreen(
                imageUri = "content://test/image.jpg",
                onNext = { nextClicks++ },
                onBack = { backClicks++ }
            )
        }

        composeRule.onNodeWithText("Retake").performClick()
        composeRule.onNodeWithText("Confirm").performClick()

        assertEquals(1, backClicks)
        assertEquals(1, nextClicks)
    }
}