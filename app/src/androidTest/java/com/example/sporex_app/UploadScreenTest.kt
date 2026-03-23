package com.example.sporex_app

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.sporex_app.ui.components.UploadScreen
import org.junit.Rule
import org.junit.Test

class UploadScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun uploadScreen_showsInitialTexts_andContinueDisabledUntilImagePicked() {
        composeRule.setContent {
            UploadScreen(
                onBack = {},
                onNext = {}
            )
        }

        composeRule.onNodeWithText("← Back").assertIsDisplayed().assertHasClickAction()
        composeRule.onNodeWithText("Upload Mould Image").assertIsDisplayed()
        composeRule.onNodeWithText("Tap to upload a photo").assertIsDisplayed()
        composeRule.onNodeWithText("Supported formats: JPG or PNG").assertIsDisplayed()

        composeRule.onNodeWithText("Image selected. Press Continue to proceed.").assertDoesNotExist()
        composeRule.onNodeWithText("Continue").assertExists()
    }
}