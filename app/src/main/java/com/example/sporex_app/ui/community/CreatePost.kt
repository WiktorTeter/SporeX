package com.example.sporex_app.ui.community

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sporex_app.R
import com.example.sporex_app.ui.navigation.BottomNavBar
import com.example.sporex_app.ui.navigation.TopBar
import com.example.sporex_app.ui.theme.SPOREX_AppTheme
import android.content.Intent
import androidx.compose.ui.platform.LocalContext

class CreatePostActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            SPOREX_AppTheme {

                Scaffold(
                    topBar = { TopBar() },
                    bottomBar = { BottomNavBar(currentScreen = "community") }
                ) { padding ->

                    CreatePostScreen(
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}

@Composable
fun CreatePostScreen(
    modifier: Modifier = Modifier
) {

    var postContent by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.sporex_green))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Create Post",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.sporex_black)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.sporex_white)
            ),
            border = BorderStroke(
                2.dp,
                colorResource(id = R.color.sporex_black)
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "What's on your mind?",
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.sporex_black)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = postContent,
                    onValueChange = { postContent = it },
                    placeholder = {
                        Text(
                            "Share your mold experience or ask for advice...",
                            color = colorResource(id = R.color.sporex_text_muted)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.sporex_black),
                        unfocusedBorderColor = colorResource(id = R.color.sporex_black),
                        cursorColor = colorResource(id = R.color.sporex_black)
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                val context = LocalContext.current

                Button(
                    onClick = {
                        val activity = context as? Activity ?: return@Button

                        val resultIntent = Intent().apply {
                            putExtra("post_content", postContent)
                        }

                        activity.setResult(Activity.RESULT_OK, resultIntent)
                        activity.finish()
                    },
                    enabled = postContent.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.sporex_black),
                        contentColor = colorResource(id = R.color.sporex_white)
                    )
                ) {
                    Text("Post")
                }
            }
        }
    }
}
