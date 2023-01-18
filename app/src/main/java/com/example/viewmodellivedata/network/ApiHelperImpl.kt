package com.example.viewmodellivedata.network

import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.model.News
import com.example.viewmodellivedata.utils.Constants
import retrofit2.Call
import retrofit2.Response

class ApiHelperImpl(private val NewsApiService:NewsApiService):NewsApiHelper {
    override suspend fun getAllNews(): Response<News> = NewsApiService.getAllNews(
        Constants.COUNTRY,
        Constants.CATEGORY,
        Constants.API_KEY)
}