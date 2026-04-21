package com.example.sporex_app.ui.alerts

import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sporex_app.ui.alerts.VentilationScheduler
import com.example.sporex_app.ui.alerts.NotificationItem
import com.example.sporex_app.ui.alerts.AlertType
import androidx.core.app.NotificationCompat
import com.example.sporex_app.ui.navigation.BottomNavBar
import com.example.sporex_app.ui.navigation.TopBar
import com.example.sporex_app.ui.theme.SPOREX_AppTheme
import com.example.sporex_app.utils.isDarkMode


class NotificationsActivity : ComponentActivity() {

    private val notificationsList = listOf(
        NotificationItem(
            title = "Air Quality Warning",
            message = "CO₂ levels are higher than recommended.",
            time = "5 mins ago",
            type = AlertType.WARNING
        ),
        NotificationItem(
            title = "Mold Detected",
            message = "Potential mold detected.",
            time = "12 mins ago",
            type = AlertType.CRITICAL
        ),
        NotificationItem(
            title = "CO₂ Normalized",
            message = "Air is back to safe levels.",
            time = "1 hour ago",
            type = AlertType.INFO
        )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val prefs = getSharedPreferences("ventilation", Context.MODE_PRIVATE)
        val interval = prefs.getInt("interval", 3)

        VentilationScheduler.schedule(this, interval)

        VentilationScheduler.schedule(this, interval)

        setContent {
            SPOREX_AppTheme(darkTheme = isDarkMode(this)) {
                NotificationsScreen(notificationsList)
            }
        }
    }
    private fun sendTestNotification(context: Context) {

        val channelId = "sporex_notifications"

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
                ?: return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // must be valid
            .setContentTitle("Sporex Update")
            .setContentText("You have a new activity notification!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        // Safe notify
        notificationManager.notify(1, notification)
    }
}

@Composable
fun NotificationsScreen(notifications: List<NotificationItem>) {
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavBar(currentScreen = "alerts") },
        containerColor = MaterialTheme.colorScheme.primary
    ) { paddingValues ->

        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {

            items(notifications) { notification ->
                NotificationCard(notification)
            }

        }
    }
}

@Composable
fun NotificationCard(notification: NotificationItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = notification.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = notification.message,
            fontSize = 14.sp,
            // Changed to onPrimary (or a slightly transparent version of it)
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = notification.time,
            fontSize = 12.sp,
            // Adjusted for visibility on green
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
            modifier = Modifier.align(Alignment.End)
        )
    }
}