package com.example.sporex_app

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.sporex_app.ui.community.Comment
import com.example.sporex_app.ui.community.CommunityPost
import com.example.sporex_app.ui.community.CommunityScreen
import org.junit.Rule
import org.junit.Test

class CommunityScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun communityScreen_showsInitialPosts() {
        val postsState = mutableStateOf(
            mutableListOf(
                CommunityPost(1, "Alice", "Black mold near bathroom window.", "2h ago"),
                CommunityPost(2, "Bob", "Damp wall in living room.", "5h ago")
            )
        )

        composeRule.setContent {
            CommunityScreen(postsState = postsState)
        }

        composeRule.onNodeWithText("Alice").assertIsDisplayed()
        composeRule.onNodeWithText("Black mold near bathroom window.").assertIsDisplayed()
        composeRule.onNodeWithText("Bob").assertIsDisplayed()
        composeRule.onNodeWithText("Damp wall in living room.").assertIsDisplayed()
    }

    @Test
    fun myPostsFilter_showsOnlyUserPosts() {
        val postsState = mutableStateOf(
            mutableListOf(
                CommunityPost(1, "Alice", "Alice post", "2h ago"),
                CommunityPost(2, "You", "My own post", "Just now")
            )
        )

        composeRule.setContent {
            CommunityScreen(postsState = postsState)
        }

        composeRule.onNodeWithText("My Posts").performClick()

        composeRule.onNodeWithText("My own post").assertIsDisplayed()
        composeRule.onNodeWithText("Alice post").assertDoesNotExist()
    }

    @Test
    fun likeButton_togglesCount() {
        val postsState = mutableStateOf(
            mutableListOf(
                CommunityPost(1, "Alice", "Need help with mold.", "2h ago")
            )
        )

        composeRule.setContent {
            CommunityScreen(postsState = postsState)
        }

        composeRule.onNodeWithText("♡ 0").assertExists().performClick()
        composeRule.onNodeWithText("♥ 1").assertIsDisplayed()

        composeRule.onNodeWithText("♥ 1").performClick()
        composeRule.onNodeWithText("♡ 0").assertIsDisplayed()
    }

    @Test
    fun deleteButton_removesPost() {
        val postsState = mutableStateOf(
            mutableListOf(
                CommunityPost(1, "Alice", "Delete me", "2h ago"),
                CommunityPost(2, "Bob", "Keep me", "5h ago")
            )
        )

        composeRule.setContent {
            CommunityScreen(postsState = postsState)
        }

        composeRule.onAllNodesWithText("Delete")[0].performClick()

        composeRule.onNodeWithText("Delete me").assertDoesNotExist()
        composeRule.onNodeWithText("Keep me").assertIsDisplayed()
    }

    @Test
    fun commentDialog_allowsAddingComment() {
        val postsState = mutableStateOf(
            mutableListOf(
                CommunityPost(1, "Alice", "Any advice?", "2h ago", comments = mutableListOf())
            )
        )

        composeRule.setContent {
            CommunityScreen(postsState = postsState)
        }

        composeRule.onNodeWithText("Comments (0)").performClick()
        composeRule.onNodeWithText("Comments").assertIsDisplayed()

        composeRule.onNodeWithText("Add comment...").performTextInput("Try ventilating the room.")
        composeRule.onNodeWithText("Comment").performClick()

        composeRule.onNodeWithText("You: Try ventilating the room.").assertIsDisplayed()
    }
}