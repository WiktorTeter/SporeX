package com.example.sporex_app.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.sporex_app.ui.components.ProductsActivity
import com.example.sporex_app.ui.components.UploadActivity

@Composable

fun MainScreen(paddingValues: PaddingValues) {
    val context = LocalContext.current

    HomeScreen(
        modifier = Modifier.padding(paddingValues),
        onUploadClick = {
            context.startActivity(Intent(context, UploadActivity::class.java))
        },
        onProductsClick = {
            context.startActivity(Intent(context, ProductsActivity::class.java))
        }
    )
}
