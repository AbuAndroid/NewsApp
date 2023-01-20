package com.example.viewmodellivedata.di.module

import com.example.viewmodellivedata.network.NewsApiService
import com.example.viewmodellivedata.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestHelper : KoinComponent {

    private fun loggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    private fun httpClient() =
        OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor())
        }.build()

    private val retrofit = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        addConverterFactory(GsonConverterFactory.create())
        client(httpClient())
    }.build()

    val client: NewsApiService by lazy { retrofit.create(NewsApiService::class.java) }
}