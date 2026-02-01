package com.example.sporex_app

import android.app.Instrumentation
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.anyIntent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import com.example.sporex_app.ui.components.UploadActivity
import com.example.sporex_app.ui.navigation.BottomNavBar
import com.example.sporex_app.ui.useraccount.ProfileActivity
import com.example.sporex_app.ui.device.DeviceActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BottomNavBarIntentTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        Intents.init()
        // Stop real activity launches during tests
        intending(anyIntent()).respondWith(Instrumentation.ActivityResult(0, null))
    }

    @After
    fun teardown() {
        Intents.release()
    }

    @Test
    fun clickingHome_launchesMainActivity() {
        composeRule.setContent { BottomNavBar(currentScreen = "devices") }
        composeRule.onNodeWithContentDescription("home").performClick()
        intended(hasComponent(MainActivity::class.java.name))
    }

    @Test
    fun clickingDevices_launchesDeviceActivity() {
        composeRule.setContent { BottomNavBar(currentScreen = "home") }
        composeRule.onNodeWithContentDescription("devices").performClick()
        intended(hasComponent(DeviceActivity::class.java.name))
    }

    @Test
    fun clickingCamera_launchesUploadActivity() {
        composeRule.setContent { BottomNavBar(currentScreen = "home") }
        composeRule.onNodeWithContentDescription("camera").performClick()
        intended(hasComponent(UploadActivity::class.java.name))
    }

    @Test
    fun clickingProfile_launchesProfileActivity() {
        composeRule.setContent { BottomNavBar(currentScreen = "home") }
        composeRule.onNodeWithContentDescription("profile").performClick()
        intended(hasComponent(ProfileActivity::class.java.name))
    }
}
