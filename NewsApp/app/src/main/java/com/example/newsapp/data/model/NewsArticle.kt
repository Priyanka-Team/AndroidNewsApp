package com.example.newsapp.data.model

data class NewsArticle(
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val content:String,
    val urlToImage: String?,
    val publishedAt: String
)
