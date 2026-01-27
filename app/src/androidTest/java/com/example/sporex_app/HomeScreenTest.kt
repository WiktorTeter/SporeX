package com.example.sporex_app

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.sporex_app.ui.screens.HomeScreen
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun quickScanButton_callsCallback_once() {
        var clicks = 0

        composeRule.setContent {
            HomeScreen(onUploadClick = { clicks++ })
        }

        composeRule.onNodeWithText("Quick Scan").assertExists().performClick()

        assertEquals(1, clicks)
    }
}
