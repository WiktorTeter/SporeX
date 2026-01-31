package com.example.sporex_app.ui.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class CommunityPost(
    val id: Int,
    val author: String,
    val content: String
)

@Composable
fun CommunityScreen() {
    var posts by remember {
        mutableStateOf(
            listOf(
                CommunityPost(1, "Alice", "Noticed black mold near my bathroom window. Any tips to clean it safely?"),
                CommunityPost(2, "Bob", "My living room wall has some damp patches. Should I call a professional?"),
                CommunityPost(3, "Carol", "Used SporeX to detect mold behind my couch—worked like a charm!")
            )
        )
    }

    var newPostContent by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "SporeX Community",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // New post input
        OutlinedTextField(
            value = newPostContent,
            onValueChange = { newPostContent = it },
            label = { Text("Share your mold detection story or ask for advice...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val trimmedContent = newPostContent.trim()
                if (trimmedContent.isNotEmpty()) {
                    val newPost = CommunityPost(
                        id = posts.size + 1,
                        author = "You",
                        content = trimmedContent
                    )
                    posts = posts + newPost
                    newPostContent = ""
                }
            },
            modifier = Modifier.align(Alignment.End),
            enabled = newPostContent.isNotBlank() // disable when empty
        ) {
            Text("Post")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Community feed (scrollable)
        LazyColumn(
            modifier = Modifier
                .weight(1f) // take remaining space
        ) {
            items(posts) { post ->
                CommunityPostItem(post)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun CommunityPostItem(post: CommunityPost) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFDDEFEF), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(
            text = post.author,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color(0xFF0D3B66)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = post.content,
            fontSize = 14.sp,
            color = Color(0xFF1F4E5F)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CommunityScreenPreview() {
    CommunityScreen()
}
