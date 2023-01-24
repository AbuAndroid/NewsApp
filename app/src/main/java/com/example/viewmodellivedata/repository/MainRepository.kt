package com.example.viewmodellivedata.repository

import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.network.NewsApiService
import com.example.viewmodellivedata.utils.Constants



class MainRepository(private val NewApiService:NewsApiService,private val SharedPreferenceHandler:SharedPreferenceHandler){
    suspend fun getAllNews() = NewApiService.getAllNews(Constants.SOURSES,Constants.API_KEY)
    fun gettAllSavedNews() = SharedPreferenceHandler.getSavedList()
    fun saveItem(article: Article) = SharedPreferenceHandler.setSaveItme(article)
    fun removeItemSavedList(article: Article) = SharedPreferenceHandler.setRemoveItem(article)
}