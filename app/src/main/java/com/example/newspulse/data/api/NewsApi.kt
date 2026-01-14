package com.example.newspulse.data.api

import com.example.newspulse.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("search")
    suspend fun searchNews(
        @Query("q") q: String,
        @Query("lang") lang: String = "en",
        @Query("country") country: String = "in",
        @Query("apikey") apiKey: String
    ): NewsResponse
}
