package com.myandroid.hwahae.network

data class RepoDetail(
    val statusCode: Int,
    val body: Detail
)

data class Detail(
    val id: Int,
    val full_size_image: String,
    val title: String,
    val description: String,
    val price: Int,
    val oily_score: Int,
    val dry_score: Int,
    val sensitive_score: Int
)