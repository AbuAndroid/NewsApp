package com.example.viewmodellivedata.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.viewmodellivedata.R
import com.example.viewmodellivedata.adapter.NewsAdapter
import com.example.viewmodellivedata.databinding.ActivityMainBinding
import com.example.viewmodellivedata.databinding.ActivityNewsSavedItemsBinding
import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.viewmodel.NewsSavedItemsViewModel
import com.example.viewmodellivedata.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsSavedItems : AppCompatActivity() {
    private var binding: ActivityNewsSavedItemsBinding? = null
    private val newssavedViewModel: NewsSavedItemsViewModel by viewModel()
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(
            allNewsList = mutableListOf(),
            onLinkClicked = this::viewWebsite,
            onSaveOrDeleteItem = this::onSaveOrDeleteItem
        )
    }

    private fun viewWebsite(Article:Article) {
        val link = Article.url
        val bundle = Bundle()
        bundle.putString("link", link)
        val intent = Intent(this, NewsWebView::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsSavedItemsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setUpUi()
        setUpObserver()
    }

    private fun setUpObserver() {
        newssavedViewModel.news.observe(this){
            renderList(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(news: List<Article>?) {
        if (news != null) {
            newsAdapter.onNewsChanged(news)
        }
        newsAdapter.notifyDataSetChanged()
    }

    private fun setUpUi() {
        binding?.uiRvSavedlist?.adapter = newsAdapter
    }
    private fun onSaveOrDeleteItem(article: Article){

    }
}