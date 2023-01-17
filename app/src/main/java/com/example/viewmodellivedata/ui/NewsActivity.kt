package com.example.viewmodellivedata.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodellivedata.R
import com.example.viewmodellivedata.adapter.NewsAdapter
import com.example.viewmodellivedata.databinding.ActivityMainBinding
import com.example.viewmodellivedata.viewmodel.NewsViewModel

class NewsActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    private var viewModel:NewsViewModel?=null
    private var newsAdapter:NewsAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpUi()
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel?.popularNews()
        viewModel?.observeNewsLiveData()?.observe(this, Observer { newsList->
            newsAdapter?.onNewsChanged(newsList)
        })
    }

    private fun setUpUi() {
        newsAdapter = NewsAdapter()
        binding.uiRvNewsContainer.adapter=newsAdapter
    }
}