package com.example.viewmodellivedata.network

import com.example.viewmodellivedata.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object NewsApiInstance {
    val newsInstance: NewsApiService
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsApiService::class.java)
    }
}