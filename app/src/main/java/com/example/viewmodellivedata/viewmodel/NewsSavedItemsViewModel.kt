package com.example.viewmodellivedata.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.repository.MainRepository
import kotlinx.coroutines.launch

class NewsSavedItemsViewModel(private val repository: MainRepository):ViewModel() {
    var newsLiveData = MutableLiveData<List<Article>>()
    init {
        fetchAllSavedList()
    }

    private fun fetchAllSavedList() {
        viewModelScope.launch {
            repository.getAllSavedNews().let {
                newsLiveData.postValue(it)
            }
        }
    }

    fun removeItem(item: Article) {
        repository.removeItemSavedList(item)
    }
}