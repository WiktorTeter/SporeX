package com.example.sporex_app.ui.community

data class Post(
    val id: Int,
    val author: String,
    val content: String,
    val timestamp: String
)

data class Comment(
    val id: Int,
    val author: String,
    val content: String
)

data class CommunityPost(
    val id: Int,
    val author: String,
    val content: String,
    val timestamp: String,
    var likes: Int = 0,
    var isLiked: Boolean = false,
    val comments: MutableList<Comment> = mutableListOf()
)