package com.example.viewmodellivedata.di.module


import com.example.viewmodellivedata.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel {
        NewsViewModel(get(),get())
    }
}