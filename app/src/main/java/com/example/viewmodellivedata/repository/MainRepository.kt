package com.example.viewmodellivedata.repository

import com.example.viewmodellivedata.network.NewsApi
import com.example.viewmodellivedata.network.NewsApiInstance
import com.example.viewmodellivedata.utils.Constants

class MainRepository(private val NewsApiInstance: NewsApi) {
    fun getAllNews() = NewsApiInstance.getNewsAllNews(Constants.COUNTRY,Constants.CATEGORY,Constants.API_KEY)
}