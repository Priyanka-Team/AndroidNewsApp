package com.example.newsapp.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import androidx.compose.ui.graphics.Color
@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val drawableId = context.resources.getIdentifier("ic_news", "drawable", context.packageName)

    var isVisible by remember { mutableStateOf(false) }

    // Start animation
    LaunchedEffect(Unit) {
        isVisible = true
        delay(2000) // Keep splash screen for 2 seconds
        navController.navigate("newsList") {
            popUpTo("splash") { inclusive = true } // Remove splash from back stack
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //image
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = "Splash Logo",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp)) // Space between image and text

            //Splash text
            Text(
                text = "Welcome to NewsApp",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        }
    }
}
