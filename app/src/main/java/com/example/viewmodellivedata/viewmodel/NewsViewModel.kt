package com.example.viewmodellivedata.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.model.News
import com.example.viewmodellivedata.network.NewsApiInstance
import com.example.viewmodellivedata.repository.MainRepository
import com.example.viewmodellivedata.utils.Constants
import com.example.viewmodellivedata.utils.Constants.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class NewsViewModel(private val repository: MainRepository) : ViewModel() {
    var newsLiveData = MutableLiveData<List<Article>>()
    fun getAllNews(){
        val response = repository.getAllNews()
        response.enqueue(object : retrofit2.Callback<News?> {
            override fun onResponse(call: Call<News?>, response: Response<News?>) {
                newsLiveData.postValue(response.body()?.articles)
            }

            override fun onFailure(call: Call<News?>, t: Throwable) {
                Log.e("error",t.toString())
            }
        })
    }


   // fun popularNews() {
//        NewsApiInstance.newsInstance.getNewsAllNews(
//            country = Constants.COUNTRY,
//            category = Constants.CATEGORY,
//            apiKey = Constants.API_KEY
//        ).enqueue(object : retrofit2.Callback<Article?> {
//            override fun onResponse(call: Call<Article?>, response: Response<Article?>) {
//
//            }
//
//            override fun onFailure(call: Call<Article?>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        })
//    }
//    fun observeNewsLiveData(): MutableLiveData<List<Article>> {
//        return newsLiveData
//    }

}