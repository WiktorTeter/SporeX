package com.example.sporex_app

import android.os.Build
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import com.example.sporex_app.network.PostResponse
import com.example.sporex_app.ui.community.CommunityScreen
import com.example.sporex_app.ui.community.CreatePostScreen
import com.example.sporex_app.ui.theme.SPOREX_AppTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = Build.VERSION_CODES.O)
class CommunityFrontendTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val samplePosts = listOf(
        PostResponse(
            id = "post1",
            user_name = "Wiktor",
            post_name = "Post",
            content = "Mould found near the window.",
            created_at = "2026-05-01T12:00:00",
            replies = emptyList(),
            category = "mould",
            image_url = null
        ),
        PostResponse(
            id = "post2",
            user_name = "Meghan",
            post_name = "Post",
            content = "Humidity is very high in my room.",
            created_at = "2026-05-01T13:00:00",
            replies = emptyList(),
            category = "health",
            image_url = null
        ),
        PostResponse(
            id = "post3",
            user_name = "Wiktor",
            post_name = "Post",
            content = "Testing my own second post.",
            created_at = "2026-05-01T14:00:00",
            replies = emptyList(),
            category = "misc",
            image_url = null
        )
    )

    @Test
    fun communityScreen_displaysPostsAndFilterChips() {
        composeTestRule.setContent {
            SPOREX_AppTheme {
                CommunityScreen(
                    posts = samplePosts,
                    currentUsername = "Wiktor",
                    onAddReply = { _, _ -> },
                    onDeletePost = { _ -> }
                )
            }
        }

        composeTestRule.onNodeWithText("All").assertIsDisplayed()
        composeTestRule.onNodeWithText("My Posts").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mould").assertIsDisplayed()
        composeTestRule.onNodeWithText("Health").assertIsDisplayed()
        composeTestRule.onNodeWithText("Misc").assertIsDisplayed()

        composeTestRule.onNodeWithText("Mould found near the window.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Humidity is very high in my room.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Testing my own second post.").assertIsDisplayed()
    }

    @Test
    fun myPostsFilter_onlyShowsCurrentUsersPosts() {
        composeTestRule.setContent {
            SPOREX_AppTheme {
                CommunityScreen(
                    posts = samplePosts,
                    currentUsername = "Wiktor",
                    onAddReply = { _, _ -> },
                    onDeletePost = { _ -> }
                )
            }
        }

        composeTestRule.onNodeWithText("My Posts").performClick()

        composeTestRule.onNodeWithText("Mould found near the window.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Testing my own second post.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Humidity is very high in my room.").assertIsNotDisplayed()
    }

    @Test
    fun categoryFilter_onlyShowsSelectedCategory() {
        composeTestRule.setContent {
            SPOREX_AppTheme {
                CommunityScreen(
                    posts = samplePosts,
                    currentUsername = "Wiktor",
                    onAddReply = { _, _ -> },
                    onDeletePost = { _ -> }
                )
            }
        }

        composeTestRule.onNodeWithText("Health").performClick()

        composeTestRule.onNodeWithText("Humidity is very high in my room.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mould found near the window.").assertIsNotDisplayed()
        composeTestRule.onNodeWithText("Testing my own second post.").assertIsNotDisplayed()
    }

    @Test
    fun deleteButton_onlyShowsForCurrentUsersPosts() {
        composeTestRule.setContent {
            SPOREX_AppTheme {
                CommunityScreen(
                    posts = samplePosts,
                    currentUsername = "Wiktor",
                    onAddReply = { _, _ -> },
                    onDeletePost = { _ -> }
                )
            }
        }

        composeTestRule.onAllNodesWithText("Delete").assertCountEquals(2)
    }

    @Test
    fun clickingDelete_callsDeleteCallbackWithCorrectPostId() {
        var deletedPostId = ""

        composeTestRule.setContent {
            SPOREX_AppTheme {
                CommunityScreen(
                    posts = listOf(samplePosts[0]),
                    currentUsername = "Wiktor",
                    onAddReply = { _, _ -> },
                    onDeletePost = { postId ->
                        deletedPostId = postId
                    }
                )
            }
        }

        composeTestRule.onNodeWithText("Delete").performClick()

        assertEquals("post1", deletedPostId)
    }

    @Test
    fun commentsButton_opensCommentDialogAndSubmitsComment() {
        var replyPostId = ""
        var replyContent = ""

        composeTestRule.setContent {
            SPOREX_AppTheme {
                CommunityScreen(
                    posts = listOf(samplePosts[0]),
                    currentUsername = "Wiktor",
                    onAddReply = { postId, comment ->
                        replyPostId = postId
                        replyContent = comment
                    },
                    onDeletePost = { _ -> }
                )
            }
        }

        composeTestRule.onNodeWithText("Comments (0)").performClick()

        composeTestRule.onNodeWithText("Comments").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add comment...").performTextInput("This is a test reply")
        composeTestRule.onNodeWithText("Comment").performClick()

        assertEquals("post1", replyPostId)
        assertEquals("This is a test reply", replyContent)
    }

    @Test
    fun createPostScreen_postButtonDisabledUntilTextIsEntered() {
        composeTestRule.setContent {
            SPOREX_AppTheme {
                CreatePostScreen()
            }
        }

        composeTestRule.onNodeWithText("Post").assertIsNotEnabled()

        composeTestRule
            .onNodeWithText("Share your mold experience or ask for advice...")
            .performTextInput("This is a frontend test post.")

        composeTestRule.onNodeWithText("Post").assertIsEnabled()
    }
}