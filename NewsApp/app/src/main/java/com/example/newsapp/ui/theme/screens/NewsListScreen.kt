package com.example.newsapp.ui.theme.screens

import NewsViewModel
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.utils.ApiKeyProvider


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(navController: NavController,viewModel: NewsViewModel,context: Context) {
    //api key
    val apiKey = remember { ApiKeyProvider.getApiKey(context) }
    LaunchedEffect(Unit) { viewModel.fetchNews(apiKey) }

    val news by viewModel.news.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Top News") }
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(news) { article ->
                NewsItem(article, navController, viewModel)
            }
        }
    }
}
@Composable
fun NewsItem(article: NewsArticle, navController: NavController, viewModel: NewsViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("newsDetail/${article.title}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            //image
            Image(
                painter = rememberImagePainter(data = article.urlToImage ?: "https://th.bing.com/th/id/OIP.d_HzudHsZOaBWhMoervcigHaD1?w=1536&h=796&rs=1&pid=ImgDetMain"),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            //description
            Text(
                text = article.description?:"The Latest news of the US",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(8.dp)
            )
            //author
            Text(
                text = article.author ?: "Author",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
