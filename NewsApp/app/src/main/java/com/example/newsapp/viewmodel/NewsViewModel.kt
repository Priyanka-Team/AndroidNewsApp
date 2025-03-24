import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val repository = NewsRepository(RetrofitClient.api)
    private val _news = MutableStateFlow<List<NewsArticle>>(emptyList())
    val news: StateFlow<List<NewsArticle>> = _news

    //fetch news details from api
    fun fetchNews(apiKey: String) {
        viewModelScope.launch {
            try {
                _news.value = repository.getTopHeadlines(apiKey).articles
            } catch (e: Exception) {
                e.message
            }
        }
    }

    fun getArticleByTitle(title: String): NewsArticle? {
        return news.value.find { it.title == title }
    }
}
