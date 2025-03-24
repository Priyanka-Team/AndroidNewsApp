import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.newsapp.data.model.NewsArticle
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(navController: NavController, article: NewsArticle) {
    val scope = rememberCoroutineScope()

    var likes by remember { mutableStateOf(0) }
    var comments by remember { mutableStateOf(0) }

    val articleId = getArticleId(article.url)

    // Fetch likes and comments when the screen is displayed
    LaunchedEffect(articleId) {
        scope.launch {
            likes = fetchLikes(articleId)
            comments = fetchComments(articleId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                // Article Image
                Image(
                    painter = rememberImagePainter(data = article.urlToImage ?: "https://via.placeholder.com/200"),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Article Title
                Text(text = article.title, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(4.dp))

                // Author
                Text(text = "By: ${article.author ?: "Unknown"}", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(8.dp))

                // Published Date
                Text(text = "Published: ${article.publishedAt}", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(16.dp))

                // Article Description
                Text(text = article.description ?: "No content available", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))

                // Full Content
                Text(text = article.content ?: "Full content not available", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))

                // Open Full Article Button
                Button(onClick = {
                    val encodedUrl = Uri.encode(article.url)
                    navController.navigate("webView/$encodedUrl")
                }) {
                    Text("Read More")
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Likes and Comments Row
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "❤️ Likes: $likes", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "💬 Comments: $comments", style = MaterialTheme.typography.bodyLarge)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// Function to convert URL to ARTICLEID format
fun getArticleId(articleUrl: String): String {
    return articleUrl.replace(Regex("^https?://"), "").replace("/", "-")
}

// Function to fetch likes from API
suspend fun fetchLikes(articleId: String): Int {
    return try {
        val response = withContext(Dispatchers.IO) {
            URL("https://cn-news-info-api.herokuapp.com/likes/$articleId").readText()
        }
        response.toIntOrNull() ?: 0
    } catch (e: Exception) {
        0
    }
}

// Function to fetch comments from API
suspend fun fetchComments(articleId: String): Int {
    return try {
        val response = withContext(Dispatchers.IO) {
            URL("https://cn-news-info-api.herokuapp.com/comments/$articleId").readText()
        }
        response.toIntOrNull() ?: 0
    } catch (e: Exception) {
        0
    }
}
