package com.example.viewmodellivedata.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.viewmodellivedata.R
import com.example.viewmodellivedata.adapter.NewsAdapter
import com.example.viewmodellivedata.databinding.ActivityMainBinding
import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.repository.SharedPreferenceHandler
import com.example.viewmodellivedata.utils.Status
import com.example.viewmodellivedata.viewmodel.NewsSavedItemsViewModel
import com.example.viewmodellivedata.viewmodel.NewsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val newsViewModel: NewsViewModel by viewModel()

    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(
            allNewsList = mutableListOf(),
            onLinkClicked = this::viewWebsite,
            onSaveOrDeleteItem = this::saveItemOrRemove
        )
    }
    private val news: MutableList<Article> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setUpUi()
        setUpListeners()
        setUpObservers()
    }


    private fun setUpListeners() {
        binding?.uiEtSearch?.doOnTextChanged { text, start, before, count ->
            filterUserList(text.toString())
        }
    }

    private fun filterUserList(searchText: String) {
        if (searchText.isEmpty()) {
            setNewsToUi(news)
        } else {
            val filteredList = news.filter {
                it.title.contains(searchText, true)
            }
            setNewsToUi(filteredList)
        }
    }

    private fun setNewsToUi(news: List<Article>) {
        newsAdapter.onNewsChanged(news)
    }

    private fun setUpObservers() {
        newsViewModel.news.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding?.progressBar?.visibility = View.GONE
                    binding?.uiRvNewsContainer?.visibility = View.VISIBLE
                    it.data?.let { allNews ->
                        renderList(allNews)
                        news.clear()
                        news.addAll(allNews)
                    }
                }
                Status.LOADING -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                    binding?.uiRvNewsContainer?.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding?.progressBar?.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(news: List<Article>) {
        newsAdapter.onNewsChanged(news)
        newsAdapter.notifyDataSetChanged()
    }

    private fun setUpUi() {
        binding?.uiRvNewsContainer?.adapter = newsAdapter
    }

    private fun viewWebsite(Article: Article) {
        val link = Article.url
        val bundle = Bundle()
        bundle.putString("link", link)
        val intent = Intent(this, NewsWebView::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun saveItemOrRemove(item: Article) {
        newsViewModel.setItem(item)
//        if(item.isSaved==false){
//            newsViewModel.setItem(item)
//        }else{
//            newsViewModel.removeItem(item)
//        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menuitems, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.uiMiSaved -> {
                val i = Intent(this,NewsSavedItems::class.java)
                startActivity(i)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}
