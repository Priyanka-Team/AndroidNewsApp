package com.example.newsapp.data.repository

import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.data.network.NewsApiService

class NewsRepository(private val apiService: NewsApiService) {
    suspend fun getTopHeadlines(apiKey: String): NewsResponse {
        return apiService.getTopHeadlines(apiKey = apiKey)
    }
}