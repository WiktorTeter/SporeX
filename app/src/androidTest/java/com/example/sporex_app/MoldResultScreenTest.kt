package com.example.sporex_app

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import com.example.sporex_app.ui.components.ResultActivity
import org.junit.Rule
import org.junit.Test

class MoldResultScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun moldResultScreen_showsDetectedMold_andRemedies() {
        composeRule.setContent {
            ResultActivity()
        }

        composeRule.onNodeWithText("Mold Detected").assertIsDisplayed()
        composeRule.onNodeWithText("Cladosporium – estimated 65% likelihood").assertIsDisplayed()
        composeRule.onNodeWithText("Suggested Remedies").assertIsDisplayed()

        composeRule.onNodeWithText("Mold Remover Spray").assertIsDisplayed()
        composeRule.onNodeWithText("Mold Removal Service").assertIsDisplayed()

        composeRule.onNodeWithText("Ask Question").assertExists().assertHasClickAction()
        composeRule.onNodeWithText("View More Remedies").assertExists().assertHasClickAction()
    }

    @Test
    fun remedyCards_showViewDetailsButtons() {
        composeRule.setContent {
            ResultActivity()
        }

        composeRule.onAllNodesWithText("View Details")[0].assertExists().assertHasClickAction()
        composeRule.onAllNodesWithText("View Details")[1].assertExists().assertHasClickAction()
    }
}