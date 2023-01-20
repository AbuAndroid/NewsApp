package com.example.viewmodellivedata.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.repository.MainRepository
import com.example.viewmodellivedata.utils.AppResult
import com.example.viewmodellivedata.utils.NetworkHelper
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    var newsLiveData = MutableLiveData<AppResult<List<Article>>>()
    val news: LiveData<AppResult<List<Article>>>
        get() = newsLiveData
    init {
        fetchAllNews()

    }

    private fun fetchAllNews() {
        viewModelScope.launch {
            newsLiveData.setValue(AppResult.loading(null))
            if (networkHelper.isNetworkConnected()) {
                repository.getAllNews().let {
                    if (it.isSuccessful) {
                        newsLiveData.setValue(AppResult.success(it.body()?.articles))
                    }else{
                        newsLiveData.setValue(AppResult.error(it.errorBody().toString(),null))
                    }
                }
            } else newsLiveData.setValue(AppResult.error("No internet connection", null))
        }
    }

    fun setItem(article:Article){
        repository.setList(article)
    }

    fun removeItem(item: Article) {
        repository.removeItemSavedList(item)
    }
}