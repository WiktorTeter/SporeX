package com.example.sporex_app

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.sporex_app.ui.community.AsthmaSociety
import org.junit.Rule
import org.junit.Test

class AsthmaSocietyTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<AsthmaSociety>()

    @Test
    fun asthmaSocietyScreen_showsMainContent() {
        composeRule.onNodeWithText("Asthma Society Collaborator Service").assertIsDisplayed()
        composeRule.onNodeWithText(
            "Connect with our medical collaborators via WhatsApp for consultation and support."
        ).assertIsDisplayed()
    }

    @Test
    fun whatsappButton_isDisplayedAndClickable() {
        composeRule.onNodeWithText("WhatsApp Service")
            .assertIsDisplayed()
            .assertHasClickAction()
    }
}