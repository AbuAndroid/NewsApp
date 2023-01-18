package com.example.viewmodellivedata.di.module

import android.content.Context
import com.example.viewmodellivedata.BuildConfig
import com.example.viewmodellivedata.network.ApiHelperImpl
import com.example.viewmodellivedata.network.NewsApiHelper
import com.example.viewmodellivedata.network.NewsApiService
import com.example.viewmodellivedata.utils.Constants
import com.example.viewmodellivedata.utils.NetworkHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    single { provideRetrofit(Constants.BASE_URL) }
    single { provideApiService(get()) }
    single { provideNetworkHelper(androidContext()) }
    single<NewsApiHelper> {
        return@single ApiHelperImpl(get())
    }
}

private fun provideNetworkHelper(context: Context) = NetworkHelper(context)
private fun provideRetrofit(BASE_URL: String): Retrofit = Retrofit.Builder()
                                                        .addConverterFactory(MoshiConverterFactory.create())
                                                        .baseUrl(BASE_URL)
                                                        .build()

private fun provideApiService(retrofit: Retrofit): NewsApiService = retrofit.create(NewsApiService::class.java)
