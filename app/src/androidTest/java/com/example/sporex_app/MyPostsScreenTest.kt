package com.example.sporex_app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.sporex_app.ui.community.MyPostsScreen
import org.junit.Rule
import org.junit.Test

class MyPostsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun myPostsScreen_showsDefaultPost() {
        composeRule.setContent {
            MyPostsScreen()
        }

        composeRule.onNodeWithText("You").assertIsDisplayed()
        composeRule.onNodeWithText("2h ago").assertIsDisplayed()
        composeRule.onNodeWithText("Just found some mold behind the couch 😱").assertIsDisplayed()
    }

    @Test
    fun myPostsScreen_showsPostActions() {
        composeRule.setContent {
            MyPostsScreen()
        }

        composeRule.onNodeWithText("Comment").assertIsDisplayed()
        composeRule.onNodeWithText("Share").assertIsDisplayed()
        composeRule.onNodeWithText("Like").assertIsDisplayed()
    }
}