package com.example.sporex_app.ui.community

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sporex_app.R
import com.example.sporex_app.ui.navigation.BottomNavBar
import com.example.sporex_app.ui.navigation.TopBar
import com.example.sporex_app.ui.community.AsthmaSociety
import com.example.sporex_app.ui.theme.SPOREX_AppTheme

class CommunityHP : ComponentActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            SPOREX_AppTheme {

                val context = LocalContext.current

                val postsState = remember {
                    mutableStateOf(
                        mutableListOf(
                            CommunityPost(
                                1,
                                "Alice",
                                "Noticed black mold near my bathroom window. Any tips?",
                                "2h ago"
                            ),
                            CommunityPost(
                                2,
                                "Bob",
                                "Damp wall in my living room. Should I call a professional?",
                                "5h ago"
                            )
                        )
                    )
                }

                val createPostLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.StartActivityForResult()
                ) { result ->

                    if (result.resultCode == Activity.RESULT_OK) {

                        val content = result.data?.getStringExtra("post_content")
                            ?: return@rememberLauncherForActivityResult

                        postsState.value = postsState.value.toMutableList().apply {
                            add(
                                0,
                                CommunityPost(
                                    id = (maxOfOrNull { it.id } ?: 0) + 1,
                                    author = "You",
                                    content = content,
                                    timestamp = "Just now"
                                )
                            )
                        }
                    }
                }
                Scaffold(
                    topBar = { TopBar() },
                    bottomBar = { BottomNavBar(currentScreen = "community") },

                    floatingActionButton = {

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                            FloatingActionButton(
                                onClick = {
                                    createPostLauncher.launch(
                                        Intent(context, CreatePostActivity::class.java)
                                    )
                                },
                                containerColor = colorResource(id = R.color.sporex_black),
                                contentColor = colorResource(id = R.color.sporex_white)
                            ) {
                                Text("+", fontSize = 20.sp)
                            }

                            // MORE FAB → Asthmasociety.kt
                            FloatingActionButton(
                                onClick = {
                                    context.startActivity(
                                        Intent(context, AsthmaSociety::class.java)
                                    )
                                },
                                containerColor = colorResource(id = R.color.sporex_black),
                                contentColor = colorResource(id = R.color.sporex_white)
                            ) {
                                Text("More", fontSize = 14.sp)
                            }
                        }
                    },

                    floatingActionButtonPosition = FabPosition.Center
                ) { padding ->

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        CommunityScreen(postsState)
                    }
                }
            }
        }
    }
}


@Composable
fun CommunityScreen(
    postsState: MutableState<MutableList<CommunityPost>>
) {

    var selectedPost by remember { mutableStateOf<CommunityPost?>(null) }
    var filter by remember { mutableStateOf("Popular") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.sporex_green))
            .padding(16.dp)
    ) {

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

            FilterChip("Popular", filter == "Popular") { filter = "Popular" }
            FilterChip("My Posts", filter == "My Posts") { filter = "My Posts" }
//            FilterChip("Following", filter == "Following") { filter = "Following" }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val filteredPosts = when (filter) {

            "My Posts" -> postsState.value.filter {
                it.author == "You"
            }

//            "Following" -> postsState.value.filter {
//                it.author != "You"
//            }

            else -> postsState.value
        }

        LazyColumn {

            items(filteredPosts, key = { it.id }) { post ->

                CommunityPostCard(
                    post = post,
                    onLike = {
                        postsState.value = postsState.value.map { p ->
                            if (p.id == post.id) {
                                p.copy(
                                    isLiked = !p.isLiked,
                                    likes = p.likes + if (!p.isLiked) 1 else -1
                                )
                            } else p
                        }.toMutableList()
                    },
                    onDelete = {
                        postsState.value = postsState.value.filter {
                            it.id != post.id
                        }.toMutableList()
                    },
                    onViewFull = {
                        selectedPost = post
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }

    selectedPost?.let { post ->
        AlertDialog(
            onDismissRequest = { selectedPost = null },
            confirmButton = {},
            text = { FullPostView(post) }
        )
    }
}


@Composable
fun CommunityPostCard(
    post: CommunityPost,
    onLike: () -> Unit,
    onDelete: () -> Unit,
    onViewFull: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth(),
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
            modifier = Modifier.padding(18.dp)
        ) {

            // Author + Timestamp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    post.author,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.sporex_black)
                )

                Text(
                    post.timestamp,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.sporex_text_muted)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                post.content,
                color = colorResource(id = R.color.sporex_black),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Interaction Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                    TextButton(onClick = onLike) {
                        Text(
                            if (post.isLiked) "♥ ${post.likes}" else "♡ ${post.likes}",
                            color = colorResource(id = R.color.sporex_black)
                        )
                    }

                    TextButton(onClick = onViewFull) {
                        Text(
                            "Comments (${post.comments.size})",
                            color = colorResource(id = R.color.sporex_black)
                        )
                    }
                }

                TextButton(onClick = onDelete) {
                    Text(
                        "Delete",
                        color = colorResource(id = R.color.sporex_red)
                    )
                }
            }
        }
    }
}

@Composable
fun FullPostView(post: CommunityPost) {

    var commentText by remember { mutableStateOf("") }

    Column {

        Text(post.author, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(post.content)

        Spacer(modifier = Modifier.height(12.dp))
        Text("Comments", fontWeight = FontWeight.SemiBold)

        post.comments.forEach { comment ->
            Text("${comment.author}: ${comment.content}")
            Spacer(modifier = Modifier.height(4.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = commentText,
            onValueChange = { commentText = it },
            label = { Text("Add comment...") }
        )

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = {
                if (commentText.isNotBlank()) {
                    post.comments.add(
                        Comment(
                            id = post.comments.size + 1,
                            author = "You"
                            ,
                            content = commentText
                        )
                    )
                    commentText = ""
                }
            }
        ) {
            Text("Comment")
        }
    }
}

@Composable
fun FilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Surface(
        shape = RoundedCornerShape(50),
        color = if (selected)
            colorResource(id = R.color.sporex_black)
        else
            colorResource(id = R.color.sporex_white),
        border = BorderStroke(1.dp, colorResource(id = R.color.sporex_black)),
        modifier = Modifier.clickable { onClick() }
    ) {

        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (selected)
                colorResource(id = R.color.sporex_white)
            else
                colorResource(id = R.color.sporex_black)
        )
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

    val dummy = remember {
        mutableStateOf(
            mutableListOf(
                CommunityPost(1,"Preview","Sample post","Just now")
            )
        )
    }

    CommunityScreen(dummy)
}
