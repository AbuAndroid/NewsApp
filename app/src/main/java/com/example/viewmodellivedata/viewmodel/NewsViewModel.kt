package com.example.viewmodellivedata.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.repository.MainRepository
import com.example.viewmodellivedata.utils.NetworkHelper
import kotlinx.coroutines.launch
import com.example.viewmodellivedata.utils.CustomResponse.*

class NewsViewModel(
    private val repository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private var newsLiveData =
        MutableLiveData<List<Article>?>() //It is a Catch List. it is used to store data from server
    private var errorLd = MutableLiveData<String>() //Handle Error From Served
    private var loaderLd = MutableLiveData<Boolean>() //Handle Loading Event

    val error: LiveData<String> = errorLd
    val loader: LiveData<Boolean> = loaderLd
    val newsList: LiveData<List<Article>?> = newsLiveData
    private val allNewsList = arrayListOf<Article>()

    var flag = false
        private set

    init {
        fetchAllNews()
    }

    //fetching all data from server
    fun fetchAllNews() {
        if (networkHelper.isNetworkConnected()) {
            loaderLd.value = true
            viewModelScope.launch {
                when (val response = repository.getAllNewsFromServer()) {
                    is Success -> {
                        allNewsList.clear()
                        checkDataSavedInDb(response.data)
                        allNewsList.addAll(response.data)
                        newsLiveData.value = allNewsList
                    }
                    is Failure -> errorLd.value =
                        response.error.message //if response is falilure error message set to error Live data
                }.also { loaderLd.value = false }
            }
        }
    }

    //fetching all saved data from shared prefernce via repository
    fun fetchSavedNews(): MutableList<Article> {
        //set saved data to newsLiveData
        return repository.getAllSavedNews()
    }

    //save item to sharedPreference
    fun saveItem(article: Article) {
        repository.saveItem(article)
    }

    fun saveSpecificPosition(article: Article, position: Int) {
        repository.saveSpecificPosition(article, position)
    }

    //remove item from sharedpreference
    fun removeItem(item: Article) {
        flag = true
        repository.removeItemSavedList(item)
    }

    private fun checkDataSavedInDb(newsList: List<Article>) {
        newsList.forEach { list ->
            val savedList = fetchSavedNews()
            savedList.forEach {
                if (it.title == list.title) {
                    list.isSaved = true
                }
            }
        }
    }

    fun filterNewsList(searchText: String): List<Article> {
        val originalSavedList: MutableList<Article> = newsList.value as MutableList<Article>
        return if (searchText.isEmpty()) {
            originalSavedList
        } else {
            val filteredList = originalSavedList.filter {
                it.title.contains(searchText, true)
            }
            filteredList
        }
    }
}