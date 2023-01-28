package com.example.viewmodellivedata.repository

import android.util.Log
import com.example.viewmodellivedata.manager.SharedPreferenceHandler
import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.network.NewsApiService
import com.example.viewmodellivedata.repository.mapper.NewsResponseMapper
import com.example.viewmodellivedata.warehouse.Constants
import com.example.viewmodellivedata.utils.CustomResponse
import com.example.viewmodellivedata.utils.LocalException


class MainRepository(
    private val newApiService: NewsApiService,
    private val SharedPreferenceHandler: SharedPreferenceHandler
) {
    // suspend fun getAllNews() = NewApiService.getAllNews(Constants.SOURSES,Constants.API_KEY)
    /*This Function is Used To get AllNewsData From Server*/
    suspend fun getAllNewsFromServer(): CustomResponse<List<Article>, LocalException> =
        NewsResponseMapper.map(
            newApiService.getAllNews(
                Constants.SOURSES,
                Constants.API_KEY
            )
        )

    /*This Function is Used To Get All Saved Items in DB*/
    fun getAllSavedNews(): MutableList<Article> {
        Log.e("getSavedNews", SharedPreferenceHandler.getSavedList().toString())
        return SharedPreferenceHandler.getSavedList()
    }

    /*This function is Used to save user Selected Item in DB*/
    fun saveItem(article: Article) = SharedPreferenceHandler.saveItemToDb(article)

    /*This function is Used to Remove user Selected Item in DB*/
    fun removeItemSavedList(article: Article) = SharedPreferenceHandler.removeItemInDb(article)

    /*This function is Used to save user Selected Item to specificPosition in DB*/
    fun saveSpecificPosition(article: Article, position: Int) {
        SharedPreferenceHandler.saveItemToSpecificPosition(article, position)
    }
}