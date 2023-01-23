package com.example.viewmodellivedata.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.viewmodellivedata.adapter.NewsAdapter
import com.example.viewmodellivedata.databinding.ActivityNewsSavedItemsBinding
import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.viewmodel.NewsSavedItemsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsSavedItems : AppCompatActivity() {
    private var binding: ActivityNewsSavedItemsBinding? = null
    private val newssavedViewModel: NewsSavedItemsViewModel by viewModel()
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(
            allNewsList = mutableListOf(),
            onLinkClicked = this::viewWebsite,
            onSave = this::saveItem,
            onRemove = this::removeItem
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsSavedItemsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setUpUi()
        getUserData()
    }

    override fun onResume() {
        super.onResume()
        getUserData()
    }
    private fun getUserData() {
        newssavedViewModel.news.observe(this) {
            renderList(it)
            Log.e("observedData",it.size.toString())
        }
    }

    private fun renderList(list: List<Article>) {
        newsAdapter.onNewsChanged(list)
    }

    private fun setUpUi() {
        binding?.uiRvSavedlist?.adapter = newsAdapter
    }

    private fun saveItem(article: Article) {

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeItem(article: Article) {
        Log.e("removed","remove item clicked.."+article.title)
       // ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.ANIMATION_TYPE_SWIPE_SUCCESS))
        newssavedViewModel.removeItem(article)
        getUserData()
        Log.e("getteddata",getUserData().toString())
    }

    private fun viewWebsite(Article: Article) {
        val link = Article.url
        val bundle = Bundle()
        bundle.putString("link", link)
        val intent = Intent(this, NewsWebView::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}