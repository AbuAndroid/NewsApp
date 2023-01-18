package com.example.viewmodellivedata.repository

import com.example.viewmodellivedata.network.NewsApiHelper


class MainRepository(private val NewsApiHelper:NewsApiHelper) {

    suspend fun getAllNews() = NewsApiHelper.getAllNews()
   // fun getAllNews() = NewsApiInstance.getNewsAllNews(Constants.COUNTRY,Constants.CATEGORY,Constants.API_KEY)
}