package com.example.sporex_app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onAllNodesWithText
import com.example.sporex_app.ui.alerts.NotificationItem
import com.example.sporex_app.ui.alerts.NotificationCard
import com.example.sporex_app.ui.alerts.NotificationsScreen
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class NotificationsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun notificationCard_displaysTitleMessageAndTime() {
        composeRule.setContent {
            NotificationCard(
                notification = NotificationItem(
                    title = "Air Quality Warning",
                    message = "CO₂ levels are higher than recommended.",
                    time = "5 mins ago"
                )
            )
        }

        composeRule.onNodeWithText("Air Quality Warning").assertIsDisplayed()
        composeRule.onNodeWithText("CO₂ levels are higher than recommended.").assertIsDisplayed()
        composeRule.onNodeWithText("5 mins ago").assertIsDisplayed()
    }

    @Test
    fun notificationsScreen_displaysAllNotifications() {
        val notifications = listOf(
            NotificationItem("Air Quality Warning", "High CO₂ detected.", "5 mins ago"),
            NotificationItem("Mold Detected", "Potential mold detected on the wall.", "12 mins ago"),
            NotificationItem("CO₂ Normalized", "Levels returned to safe range.", "1 hour ago")
        )

        composeRule.setContent {
            NotificationsScreen(notifications = notifications)
        }

        composeRule.onNodeWithText("Air Quality Warning").assertIsDisplayed()
        composeRule.onNodeWithText("Mold Detected").assertIsDisplayed()
        composeRule.onNodeWithText("CO₂ Normalized").assertIsDisplayed()

        assertEquals(1, composeRule.onAllNodesWithText("5 mins ago").fetchSemanticsNodes().size + 0)
    }
}