package com.example.sporex_app.network


data class ProductSummary(
    val id: String,
    val name: String,
    val best_for: String,
    val sustainable: Boolean
)

data class ProductDetail(
    val id: String,
    val name: String,
    val sustainable: Boolean,
    val best_for: String,
    val warning: String,
    val steps: List<String>
)
