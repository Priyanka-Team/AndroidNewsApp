package com.example.newsapp

import NewsDetailScreen
import NewsViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.ui.theme.screens.NewsListScreen
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import android.os.Bundle
import androidx.compose.ui.platform.LocalContext
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.screens.SplashScreen
import com.example.newsapp.ui.theme.screens.WebViewScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                NewsApp()
            }
        }
    }
}

@Composable
fun NewsApp() {
    val navController = rememberNavController()
    val viewModel: NewsViewModel = viewModel()

    NavHost(navController = navController, startDestination = "splash") {
        //Splash Screen
        composable("splash") {
            SplashScreen(navController)
        }

        //News List Screen
        composable("newsList") {
            val context = LocalContext.current  // Get context inside Composable
            NewsListScreen(navController, viewModel, context)
        }
        //News Detail Screen
        composable("newsDetail/{title}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val article = viewModel.getArticleByTitle(title)
            if (article != null) {
                NewsDetailScreen(navController, article)
            }
        }
        //WebView Screen
        composable(
            route = "webView/{url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url")
            WebViewScreen(url = url.toString())
        }
    }
}
