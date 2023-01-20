package com.example.viewmodellivedata.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.repository.MainRepository
import com.example.viewmodellivedata.utils.AppResult
import kotlinx.coroutines.launch

class NewsSavedItemsViewModel(private val repository: MainRepository):ViewModel() {
    var newsLiveData = MutableLiveData<List<Article>>()
    val news: LiveData<List<Article>>
        get() = newsLiveData
    init {
        fetchAllSavedList()
    }

    private fun fetchAllSavedList() {
        viewModelScope.launch {
            repository.gettAllSavedNews().let {
                Log.e("saved",it.toString())
                newsLiveData.setValue(it)
            }
        }
    }
}