package com.example.sporex_app

import android.app.Activity
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import com.example.sporex_app.ui.theme.SPOREX_AppTheme
import androidx.lifecycle.lifecycleScope
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

@Composable
fun LoginScreen() {
    val context = LocalContext.current
    val activity = context as? LoginActivity

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                } else if (activity == null) {
                    Toast.makeText(context, "Error: not in LoginActivity", Toast.LENGTH_SHORT).show()
                } else {
                    isLoading = true
                    activity.performLogin(email, password) { success, message ->
                        isLoading = false
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3DDC84),
                contentColor = Color.White
            )
        ) {
            Text(if (isLoading) "Logging in..." else "Login")
        }
    }
}
