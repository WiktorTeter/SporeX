package com.example.sporex_app

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.example.sporex_app.ui.navigation.BottomNavBar
import org.junit.Rule
import org.junit.Test

class BottomNavBarTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun bottomNav_hasAllTabs_andTheyAreClickable() {
        composeRule.setContent {
            BottomNavBar(currentScreen = "home")
        }

        // contentDescription is set to item.route in BottomNavBar
        composeRule.onNodeWithContentDescription("home").assertExists().assertHasClickAction()
        composeRule.onNodeWithContentDescription("devices").assertExists().assertHasClickAction()
        composeRule.onNodeWithContentDescription("camera").assertExists().assertHasClickAction()
        composeRule.onNodeWithContentDescription("profile").assertExists().assertHasClickAction()
    }
}
