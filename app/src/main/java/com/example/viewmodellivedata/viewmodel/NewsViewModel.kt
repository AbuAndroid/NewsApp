package com.example.viewmodellivedata.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.network.NewsApiInstance
import com.example.viewmodellivedata.utils.Constants
import com.example.viewmodellivedata.utils.Constants.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class NewsViewModel : ViewModel() {
    private var newsLiveData = MutableLiveData<List<Article>>()
    fun popularNews() {
        NewsApiInstance.newsInstance.getNewsAllNews(
            country = Constants.COUNTRY,
            category = Constants.CATEGORY,
            apiKey = Constants.API_KEY
        ).enqueue(object : retrofit2.Callback<Article?> {
            override fun onResponse(call: Call<List<Article>?>, response: Response<List<Article>?>) {
                if(response.body()!=null)
                    newsLiveData.value = response.body()
            }

            override fun onFailure(call: Call<Article?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    fun observeNewsLiveData(): MutableLiveData<List<Article>> {
        return newsLiveData
    }

}