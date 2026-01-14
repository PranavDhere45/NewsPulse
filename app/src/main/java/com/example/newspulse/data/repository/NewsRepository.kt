package com.example.newspulse.data.repository

import com.example.newspulse.data.api.RetrofitInstance
import com.example.newspulse.data.model.NewsResponse

class NewsRepository {

    suspend fun getTopHeadlines(apiKey: String): NewsResponse {
        return RetrofitInstance.api.searchNews(
            q = "technology",
            apiKey = apiKey
        )
    }
    suspend fun searchNews(query: String, apiKey: String): NewsResponse {
        return RetrofitInstance.api.searchNews(
            q = query,
            apiKey = apiKey
        )
    }
    suspend fun getNewsByCategory(
        category: String,
        apiKey: String
    ): NewsResponse {
        return RetrofitInstance.api.searchNews(
            q = category,
            apiKey = apiKey
        )
    }


}
