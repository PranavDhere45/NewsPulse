package com.example.newspulse.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.data.model.Article
import com.example.newspulse.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.newspulse.data.api.RetrofitInstance


class NewsViewModel : ViewModel() {

    private val repository = NewsRepository()

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadNews() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val response = repository.getTopHeadlines(
                    apiKey = "f88601bf88c9b7b4de9b74fa87e06eab"
                )
                _articles.value = response.articles
            } catch (e: Exception) {
                _error.value = "Failed to load news"
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun searchNews(query: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val response = repository.searchNews(
                    query = query,
                    apiKey = "f88601bf88c9b7b4de9b74fa87e06eab"
                )

                _articles.value = response.articles
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun loadCategory(category: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val response = repository.getNewsByCategory(
                    category = category,
                    apiKey = "f88601bf88c9b7b4de9b74fa87e06eab"
                )

                _articles.value = response.articles
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

}



