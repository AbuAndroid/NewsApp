package com.example.viewmodellivedata.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.viewmodellivedata.adapter.NewsAdapter
import com.example.viewmodellivedata.databinding.ActivityMainBinding
import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.utils.Status
import com.example.viewmodellivedata.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding? = null
    private val newsViewModel:NewsViewModel by viewModel()
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
        setUpObservers()

//        viewModel = ViewModelProvider(this,viewModelFactory(MainRepository(NewsApiInstance = NewsApiInstance.newsInstance))).get(NewsViewModel::class.java)
//        viewModel?.newsLiveData?.observe(this, Observer {
//            Log.e("viewlivedata",it.toString())
//            newsAdapter?.onNewsChanged(it)
//        })
//
//        viewModel?.getAllNews()
//        Log.e("lg",viewModel?.getAllNews().toString())
    }

    private fun setUpObservers() {
        newsViewModel.news.observe(this, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    binding?.progressBar?.visibility = View.GONE
                    it.data?.let { news-> renderList(news)}
                }
                Status.LOADING ->{
                    binding?.progressBar?.visibility = View.VISIBLE
                    binding?.uiRvNewsContainer?.visibility = View.GONE
                }
                Status.ERROR ->{
                    binding?.progressBar?.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(news: List<Article>) {
        newsAdapter.onNewsChanged(news)
    }

    private fun setUpUi() {
        binding?.uiRvNewsContainer?.adapter=newsAdapter
    }
}