# 📰 News App (Android)

## 🚀 Overview
This app displays the latest **US Top Headlines** using [newsapi.org](https://newsapi.org).  
Users can view news articles, see the number of **likes & comments**, and open full news details.

## 📌 Features
- ✅ Displays latest **US news headlines**
- ✅ Shows **title, author, description, and image**
- ✅ Uses **placeholder image** if no image is available
- ✅ Detailed article screen with **likes & comments**
- ✅ Optimized for **both light & dark mode**

---

## 🏗️ Architecture
The app follows **MVVM** architecture:
- **Model**: API calls & data handling
- **ViewModel**: Business logic
- **View**: Jetpack Compose UI components

---

## 🛠️ Tech Stack
- **Kotlin** (Primary Language)
- **Jetpack Compose** (UI)
- **Retrofit** (API Calls)
- **Coroutines & Flow** (Async operations)
- **Hilt** (Dependency Injection)

---

## 🔑 API Configuration
1. Get an API key from [newsapi.org](https://newsapi.org).
2. Store it in `local.properties` (not committed to Git):
   ```properties
   NEWS_API_KEY=your_api_key_here
