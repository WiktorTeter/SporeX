package com.example.sporex_app.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sporex_app.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.sporex_app.ui.theme.TopBarFont

@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .background(colorResource(id = R.color.sporex_grey))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(28.dp))

            Text(
                text = "SPOREX",
                fontFamily = TopBarFont,
                fontSize = 32.sp,
                color = colorResource(id = R.color.sporex_green)
            )

            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = "Notifications",
                modifier = Modifier.size(28.dp),
                tint = colorResource(id = R.color.sporex_green)
            )
        }
    }
}


//@Composable
//fun TopBar() {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(100.dp) // reduced height
//            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
//            .background(colorResource(id = R.color.sporex_grey)),
//        contentAlignment = Alignment.Center
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(
//                text = "SPOREX",
//                fontFamily = TopBarFont,
//                fontSize = 32.sp,
//                color = colorResource(id = R.color.sporex_green)
//            )
//        }
//
//        Box(
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .padding(top = 12.dp, end = 16.dp)
//        ) {
//            Icon(
//                imageVector = Icons.Filled.Notifications,
//                contentDescription = "Notifications",
//                modifier = Modifier.size(28.dp),
//                tint = colorResource(id = R.color.sporex_green) // contrast against grey
//            )
//        }
//    }
//}


//@Composable
//fun TopBar() {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(100.dp)
//            .padding(horizontal = 20.dp)
//            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
//            .background(MaterialTheme.colorScheme.surface),
//        contentAlignment = Alignment.Center
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(
//                text = "SPOREX",
//                fontFamily = TopBarFont,
//                fontSize = 32.sp,
//                color = color.
//            )
//        }
//
//        Box(
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .padding(top = 16.dp, end = 16.dp)
//        ) {
//            Icon(
//                imageVector = Icons.Filled.Notifications,
//                contentDescription = "Notifications",
//                modifier = Modifier.size(28.dp)
//            )
//
//        }
//    }
//}

