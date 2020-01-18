package com.myandroid.hwahae.network

data class RepoPaging(
    val statusCode: Int,
    val body: List<Product>
)

data class Product(
    val id: Int,
    val thumbnail_image: String,
    val title: String,
    val price: Int,
    val oily_score: Int,
    val dry_score: Int,
    val sensitive_score: Int
)


