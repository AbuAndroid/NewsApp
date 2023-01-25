package com.example.viewmodellivedata.viewmodel


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
    private val newsLiveData = MutableLiveData<List<Article?>?>()
    private val errorLd = MutableLiveData<String>()
    private val loaderLd = MutableLiveData<Boolean>()

    val news = MutableLiveData<List<Article>>()
    val error: LiveData<String> = errorLd
    val loader: LiveData<Boolean> = loaderLd

    var newsList: LiveData<List<Article?>?> = newsLiveData

    init {
        fetchAllNews()
    }

    fun fetchAllNews() {

//            if (networkHelper.isNetworkConnected()) {
//                repository.getAllNews().let {
//                    if (it.isSuccessful) {
//                        news.value=it.body()?.articles
//                    }else{
//                        news.value = it.errorBody()
//                        newsLiveData.setValue(it.errorBody(),null)
//                    }
//                }
//            } else newsLiveData.setValue(AppResult.error("No internet connection", null))

        if (networkHelper.isNetworkConnected()) {
            loaderLd.value = true
            viewModelScope.launch {
                when (val response = repository.getAllNewsFromServer()) {
                    is Success -> newsLiveData.value = response.data
                    is Failure -> errorLd.value = response.error.message
                }.also { loaderLd.value = false }

            }
        }
    }

    fun saveItem(article: Article) {
        repository.saveItem(article)
    }

    fun removeItem(item: Article) {
        repository.removeItemSavedList(item)
    }

//    fun filterUserList(searchText: String) {
//        if (searchText.isEmpty()) {
////            renderList(news)
//        } else {
//            val filter = newsLiveData
//            val filteredList = news.filter {
//                it.title.contains(searchText, true)
//            }
//            renderList(filteredList)
//        }
//    }
}