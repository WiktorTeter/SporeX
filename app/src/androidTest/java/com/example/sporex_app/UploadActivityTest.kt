package com.example.sporex_app

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.sporex_app.ui.components.UploadActivity
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class UploadActivityTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<UploadActivity>()

    @Test
    fun initialState_showsUploadScreen_andContinueDisabled() {
        composeRule.onNodeWithText("← Back").assertExists().assertHasClickAction()
        composeRule.onNodeWithText("Upload Mould Image").assertIsDisplayed()
        composeRule.onNodeWithText("Tap to upload a photo").assertIsDisplayed()
        composeRule.onNodeWithText("Supported formats: JPG or PNG").assertIsDisplayed()
        composeRule.onNodeWithText("Continue").assertExists().assertIsNotEnabled()
        composeRule.onNodeWithText("Image selected. Press Continue to proceed.").assertDoesNotExist()
    }

    @Test
    fun clickingBack_finishesActivity() {
        composeRule.onNodeWithText("← Back").performClick()
        composeRule.waitForIdle()
        assertTrue(composeRule.activity.isFinishing)
    }
}