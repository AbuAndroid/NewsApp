package com.example.viewmodellivedata.network

import com.example.viewmodellivedata.model.Article
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi{

    @GET("v2/top-headlines")
    fun getNewsAllNews(
        @Query("country")country:String, @Query("category")category:String,@Query("apikey")apiKey:String
    ): Call<Article>
}