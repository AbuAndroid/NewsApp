package com.example.viewmodellivedata.di.module

import com.example.viewmodellivedata.repository.MainRepository
import com.example.viewmodellivedata.repository.SharedPreferenceHandler
import com.example.viewmodellivedata.utils.NetworkHelper
import com.example.viewmodellivedata.viewmodel.NewsSavedItemsViewModel
import com.example.viewmodellivedata.viewmodel.NewsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Test{
    fun modules() = repomodule + commonModule + viewModelModule
}

val repomodule = module {
    single { MainRepository(get(),get()) }
}

val viewModelModule = module {
    viewModel {
        NewsViewModel(get(),get())
    }
    viewModel{
        NewsSavedItemsViewModel(get())
    }
}

val commonModule = module {
    single {
        NetworkHelper(androidContext())
    }
    single {
        RestHelper.client
    }
    single {
        SharedPreferenceHandler(androidContext())
    }

}