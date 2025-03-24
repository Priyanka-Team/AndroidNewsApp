package com.example.newsapp.utils

import android.content.Context
import java.util.*

object ApiKeyProvider {
    fun getApiKey(context: Context): String {
        return try {
            val properties = Properties()
            context.assets.open("local.properties").use { inputStream ->
                properties.load(inputStream)
            }
            properties.getProperty("NEWS_API_KEY", "")
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}
