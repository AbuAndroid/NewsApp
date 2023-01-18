package com.example.viewmodellivedata.network

import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.model.News
import retrofit2.Call
import retrofit2.Response

interface NewsApiHelper {
    suspend fun getAllNews():Response<News>
}