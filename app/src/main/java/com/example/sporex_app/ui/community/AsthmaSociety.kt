package com.example.sporex_app.ui.community

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sporex_app.R
import com.example.sporex_app.ui.navigation.BottomNavBar
import com.example.sporex_app.ui.navigation.TopBar
import com.example.sporex_app.ui.theme.SPOREX_AppTheme

class AsthmaSociety : ComponentActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            SPOREX_AppTheme {

                Scaffold(
                    topBar = { TopBar() },
                    bottomBar = { BottomNavBar(currentScreen = "community") }
                ) { padding ->

                    AsthmaSocietyFeed(
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}

@Composable
fun AsthmaSocietyFeed(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.sporex_green))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Card(
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(
                2.dp,
                colorResource(id = R.color.sporex_black)
            ),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.sporex_white)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.asthma),
                    contentDescription = "Asthma Society Logo",
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Asthma Society Collaborator Service",
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.sporex_black)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Connect with our medical collaborators via WhatsApp for consultation and support.",
                    color = colorResource(id = R.color.sporex_text_muted),
                    fontSize = 14.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        val phoneNumber = "+353 86 059 0132"

                        val uri = Uri.parse("https://wa.me/$phoneNumber")

                        context.startActivity(
                            Intent(Intent.ACTION_VIEW, uri)
                        )
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.sporex_black),
                        contentColor = colorResource(id = R.color.sporex_white)
                    ),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("WhatsApp Service")
                }
            }
        }
    }
}