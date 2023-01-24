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
    val newsLiveData = MutableLiveData<List<Article>>()
    val news = MutableLiveData<List<Article>>()

    var _success: LiveData<List<Article>> = newsLiveData

    init {
        fetchAllNews()
    }
    fun fetchAllNews() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                repository.getAllNews().let {
                    if (it.isSuccessful) {
                        news.setValue(it.body()?.articles)
                    }else{
                        news.value = it.errorBody()
                        newsLiveData.setValue(it.errorBody(),null)
                    }
                }
            } else newsLiveData.setValue(AppResult.error("No internet connection", null))
        }
    }

    fun saveItem(article:Article){
        repository.saveItem(article)
    }

    fun removeItem(item: Article) {
        repository.removeItemSavedList(item)
    }

    fun filterUserList(searchText: String) {
        if (searchText.isEmpty()) {
//            renderList(news)
        } else {
            val filter = newsLiveData
            val filteredList = news.filter {
                it.title.contains(searchText, true)
            }
            renderList(filteredList)
        }
    }
}