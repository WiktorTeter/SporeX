package com.example.sporex_app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.sporex_app.ui.components.HistoryActivity
import org.junit.Rule
import org.junit.Test

class HistoryActivityTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<HistoryActivity>()

    @Test
    fun historyActivity_showsSectionHeaders() {
        composeRule.onNodeWithText("Last 7 Days").assertIsDisplayed()
    }

    @Test
    fun historyActivity_showsOpenAndSolvedCases() {
        composeRule.onNodeWithText("OPEN CASE").assertExists()
        composeRule.onNodeWithText("OPEN CASE").assertExists()
        composeRule.onNodeWithText("49% Chance of Aspergillus").assertExists()
        composeRule.onNodeWithText("49% Chance of Aspergillus + 10% chance of Penicillium").assertExists()
    }

    @Test
    fun historyActivity_caseCardsShowForwardIcon() {
        composeRule.onNodeWithContentDescription("View Case").assertExists()
    }
}