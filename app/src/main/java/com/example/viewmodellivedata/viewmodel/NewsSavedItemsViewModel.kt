package com.example.viewmodellivedata.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.repository.MainRepository
import kotlinx.coroutines.launch

class NewsSavedItemsViewModel(private val repository: MainRepository):ViewModel() {
    var newsLiveData = MutableLiveData<List<Article>>()
    val news: LiveData<List<Article>> = newsLiveData
    init {
        fetchAllSavedList()
    }

    fun fetchAllSavedList() {
        viewModelScope.launch {
            repository.gettAllSavedNews().let {
                newsLiveData.postValue(it)
            }
        }
    }

    fun removeItem(item: Article) {
        repository.removeItemSavedList(item)
    }
}