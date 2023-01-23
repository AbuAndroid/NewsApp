package com.example.viewmodellivedata.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.viewmodellivedata.R
import com.example.viewmodellivedata.adapter.NewsAdapter
import com.example.viewmodellivedata.databinding.ActivityMainBinding
import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.utils.NetworkHelper
import com.example.viewmodellivedata.utils.Status
import com.example.viewmodellivedata.viewmodel.NewsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val news: MutableList<Article> = mutableListOf()
    private val newsViewModel: NewsViewModel by viewModel()
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(
            allNewsList = mutableListOf(),
            onLinkClicked = this::viewWebsite,
            onSave = this::saveItem,
            onRemove = this::removeItem
        )
    }
    private val networkhelper : NetworkHelper by inject()
    private var package_name = "com.android.chrome";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        if(networkhelper.isNetworkConnected()){
            setUpUi()
            setUpListeners()
            setUpObservers()
        }else{
            startActivity(Intent(this,NewsSavedItems::class.java))
        }

    }

    private fun setUpListeners() {
        binding?.uiEtSearch?.doOnTextChanged { text, start, before, count ->
            newsViewModel.filterUserList(text.toString())
        }
    }

    private fun setUpObservers() {
        newsViewModel.news.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding?.progressBar?.visibility = View.GONE
                    binding?.uiRvNewsContainer?.visibility = View.VISIBLE
                    it.data?.let {
                        //checkDataSavedInDb(it)
                        news.clear()
                        news.addAll(it)
                        renderList(it)
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

    private fun setUpUi() {
        binding?.uiRvNewsContainer?.adapter = newsAdapter
    }

    private fun saveItem(item: Article) {
        //item.isSaved = true
        newsViewModel.setItem(item)
    }

    private fun removeItem(item: Article) {
       // item.isSaved = false
        newsViewModel.removeItem(item)
    }

    private fun viewWebsite(Article: Article) {
        val link = Article.url
        val bundle = Bundle()
        bundle.putString("link", link)
        val intent = Intent(this, NewsWebView::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun isPackageInstalled(packageName: String): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun checkDataSavedInDb(newsList: List<Article>) {
        newsList.forEach { list ->
            val savedList = newsViewModel.news.value?.data
            savedList?.forEach {
                if (it.title == list.title) {
                    list.isSaved = true
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(news: List<Article>) {
        newsAdapter.onNewsChanged(news)
        newsAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menuitems, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.uiMiSaved -> {
                val i = Intent(this, NewsSavedItems::class.java)
                startActivity(i)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}
