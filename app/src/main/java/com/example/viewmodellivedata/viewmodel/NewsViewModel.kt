package com.example.viewmodellivedata.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.model.News
import com.example.viewmodellivedata.repository.MainRepository
import com.example.viewmodellivedata.utils.AppResult
import com.example.viewmodellivedata.utils.NetworkHelper
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel(private val repository: MainRepository,private val networkHelper: NetworkHelper) : ViewModel() {
    val newsLiveData = MutableLiveData<AppResult<List<Article>>>()
    val news : LiveData<AppResult<List<Article>>>
                get() = newsLiveData

    init {
        fetchAllNews()
    }

    private fun fetchAllNews() {
        viewModelScope.launch {
            newsLiveData.postValue(AppResult.loading(null))
            if (networkHelper.isNetworkConnected()) {
                Log.e("data",repository.getAllNews().body().toString())
                repository.getAllNews().let {
                    if(it.isSuccessful){
                        Log.e("work",it.body().toString())
                       // newsLiveData.postValue(AppResult.success(it.body()?.articles))
                    }
                }
            } else newsLiveData.postValue(AppResult.error("No internet connection", null))
        }
    }
}