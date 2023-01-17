package com.example.viewmodellivedata.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodellivedata.repository.MainRepository
import com.example.viewmodellivedata.viewmodel.NewsViewModel

class viewModelFactory(private val repository: MainRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(NewsViewModel::class.java)){
            NewsViewModel(this.repository) as T
        }else{
            throw java.lang.IllegalArgumentException("ViewModel Not Found")
        }
    }
}