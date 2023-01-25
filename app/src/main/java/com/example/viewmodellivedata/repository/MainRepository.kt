package com.example.viewmodellivedata.repository

import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.network.NewsApiService
import com.example.viewmodellivedata.repository.mapper.Mapper
import com.example.viewmodellivedata.utils.Constants
import com.example.viewmodellivedata.utils.CustomResponse
import com.example.viewmodellivedata.utils.LocalException


class MainRepository(val newApiService:NewsApiService,private val SharedPreferenceHandler:SharedPreferenceHandler){
   // suspend fun getAllNews() = NewApiService.getAllNews(Constants.SOURSES,Constants.API_KEY)
   suspend fun getAllNewsFromServer(): CustomResponse<List<Article?>, LocalException> = Mapper.map(newApiService.getAllNews(Constants.SOURSES,Constants.API_KEY))
    fun gettAllSavedNews() = SharedPreferenceHandler.getSavedList()
    fun saveItem(article: Article) = SharedPreferenceHandler.setSaveItme(article)
    fun removeItemSavedList(article: Article) = SharedPreferenceHandler.setRemoveItem(article)
}