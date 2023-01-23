package com.example.viewmodellivedata.network

import com.example.viewmodellivedata.model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService{
    @GET("v2/top-headlines")
    suspend fun getAllNews(@Query("sources")sources: String,@Query("apikey")apiKey:String): Response<News>
}