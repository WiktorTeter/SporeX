package com.example.sporex_app

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.sporex_app.ui.components.ResultActivity
import com.example.sporex_app.ui.theme.SPOREX_AppTheme
import org.junit.Rule
import org.junit.Test

class MoldResultScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun mouldDetected_resultIsShown() {
        composeRule.setContent {
            SPOREX_AppTheme {
                ResultActivity().MoldResultScreen(
                    mouldDetected = true,
                    maxConfidence = 0.65,
                    imageUrl = "",
                    message = "Prediction complete"
                )
            }
        }

        composeRule.onNodeWithText("Possible Mould Detected").assertExists()
        composeRule.onNodeWithText("Moderate confidence: 65%").assertExists()
        composeRule.onNodeWithText("Analysis Summary").assertExists()
        composeRule.onNodeWithText("Confidence: 65%").assertExists()
    }

    @Test
    fun noMouldDetected_resultIsShown() {
        composeRule.setContent {
            SPOREX_AppTheme {
                ResultActivity().MoldResultScreen(
                    mouldDetected = false,
                    maxConfidence = 0.0,
                    imageUrl = "",
                    message = "Prediction complete"
                )
            }
        }

        composeRule.onNodeWithText("No Mould Detected").assertExists()
        composeRule.onNodeWithText("The model did not detect mould in this image.").assertExists()
        composeRule.onNodeWithText("No mould detected. Retake if unsure.").assertExists()
    }

    @Test
    fun resultScreen_buttonsAreShown() {
        composeRule.setContent {
            SPOREX_AppTheme {
                ResultActivity().MoldResultScreen(
                    mouldDetected = true,
                    maxConfidence = 0.8,
                    imageUrl = "",
                    message = "Prediction complete"
                )
            }
        }

        composeRule.onNodeWithText("Try Another Image").assertExists()
        composeRule.onNodeWithText("View More Remedies").assertExists()
    }

    @Test
    fun suggestedRemedies_areShown() {
        composeRule.setContent {
            SPOREX_AppTheme {
                ResultActivity().MoldResultScreen(
                    mouldDetected = true,
                    maxConfidence = 0.8,
                    imageUrl = "",
                    message = "Prediction complete"
                )
            }
        }

        composeRule.onNodeWithText("Suggested Remedies").assertExists()
        composeRule.onNodeWithText("Recommended Action").assertExists()
        composeRule.onNodeWithText("Likely mould present. Improve ventilation.").assertExists()
    }
}