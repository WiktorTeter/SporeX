package com.example.sporex_app.ui.useraccount

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.sporex_app.ui.theme.SPOREX_AppTheme
import androidx.lifecycle.lifecycleScope
import com.example.sporex_app.MainActivity
import com.example.sporex_app.network.LoginRequest
import com.example.sporex_app.network.RetrofitClient
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SPOREX_AppTheme {
            LoginScreen()
            }
        }
    }

    // Called from the composable
    fun performLogin(
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.login(LoginRequest(email, password))

                if (response.isSuccessful && response.body()?.success == true) {
                    val msg = response.body()?.message ?: "Login successful"

                    onResult(true, msg)

                    // Navigate to MainActivity
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    val msg = response.body()?.message
                        ?: "Login failed (${response.code()})"

                    onResult(false, msg)
                }
            } catch (e: Exception) {
                onResult(false, "Error: ${e.localizedMessage ?: "Unknown error"}")
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {

    val context = LocalContext.current
    val activity = context as? LoginActivity

    val sporexGreen = Color(0xFF06A546)
    val sporexBlack = Color(0xFF040F0F)
    val sporexWhite = Color(0xFFFFFFFF)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Login", color = sporexGreen) },
                navigationIcon = {
                    IconButton(onClick = { activity?.finish() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = sporexGreen
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // HEADER
            Text(
                text = "Welcome Back",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = sporexBlack,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // EMAIL
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // PASSWORD
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // FORGOT PASSWORD
            Text(
                text = "Forgot password?",
                color = sporexGreen,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        Toast.makeText(context, "Not implemented", Toast.LENGTH_SHORT).show()
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // LOGIN BUTTON
            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    } else if (activity != null) {
                        isLoading = true
                        activity.performLogin(email, password) { _, message ->
                            isLoading = false
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !isLoading,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = sporexGreen,
                    contentColor = sporexWhite
                )
            ) {
                Text(if (isLoading) "Logging in..." else "Login", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // GOOGLE LOGIN BUTTON
            OutlinedButton(
                onClick = {
                    Toast.makeText(context, "Google login not implemented", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Continue with Google")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // REGISTER LINK
            Text(
                text = "Don't have an account? Register",
                fontSize = 16.sp,
                color = sporexGreen,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    context.startActivity(Intent(context, RegisterActivity::class.java))
                }
            )
        }
    }
}
