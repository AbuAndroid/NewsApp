package com.example.viewmodellivedata.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodellivedata.R
import com.example.viewmodellivedata.adapter.NewsAdapter
import com.example.viewmodellivedata.databinding.ActivityMainBinding
import com.example.viewmodellivedata.network.NewsApi
import com.example.viewmodellivedata.network.NewsApiInstance
import com.example.viewmodellivedata.repository.MainRepository
import com.example.viewmodellivedata.viewModelFactory.viewModelFactory
import com.example.viewmodellivedata.viewmodel.NewsViewModel

class NewsActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding? = null
    private var viewModel:NewsViewModel?=null
    private val newsAdapter:NewsAdapter by lazy{
        NewsAdapter(
            allNewsList = mutableListOf()
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setUpUi()

        viewModel = ViewModelProvider(this,viewModelFactory(MainRepository(NewsApiInstance = NewsApiInstance.newsInstance))).get(NewsViewModel::class.java)
        viewModel?.newsLiveData?.observe(this, Observer {
            Log.e("viewlivedata",it.toString())
            newsAdapter?.onNewsChanged(it)
        })

        viewModel?.getAllNews()
        Log.e("lg",viewModel?.getAllNews().toString())
    }

    private fun setUpUi() {
       // newsAdapter = NewsAdapter()
        binding?.uiRvNewsContainer?.adapter=newsAdapter
    }
}