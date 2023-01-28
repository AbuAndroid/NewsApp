package com.example.viewmodellivedata.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.widget.doOnTextChanged
import com.example.viewmodellivedata.R
import com.example.viewmodellivedata.adapter.NewsAdapter
import com.example.viewmodellivedata.databinding.ActivityMainBinding
import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.utils.NetworkHelper
import com.example.viewmodellivedata.viewmodel.NewsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val newsViewModel: NewsViewModel by viewModel()
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(
            allNewsList = mutableListOf(),
            onLinkClicked = this::visitNewsWebsite,
            onSaveOrRemove = this::onSaveOrRemove
        )
    }
    private val upDateUi by lazy {
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ::upDate
        )
    }
    private var chromePackageName = "com.android.chrome"

    private val networkHelper: NetworkHelper by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        upDateUi
        if (networkHelper.isNetworkConnected()) {
            setUpUi()
            setUpListeners()
        } else {
            startActivity(Intent(this, NewsSavedItemsActivity::class.java))
        }
    }

    private fun setUpUi() {
        binding?.uiRvNewsContainer?.adapter = newsAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpListeners() {
        newsViewModel.error.observe(this, ::handleError)
        newsViewModel.loader.observe(this, ::handleLoaderVisibility)
        newsViewModel.newsList.observe(this, ::setNewsListToAdapter)
        binding?.uiEtSearch?.doOnTextChanged { text, _, _, _ ->
            setNewsListToAdapter(newsViewModel.filterNewsList(text.toString()))
        }
        binding?.uiSwipeRefresh?.setOnRefreshListener {
            binding!!.uiSwipeRefresh.isRefreshing = false
            newsViewModel.fetchAllNews()
            newsAdapter.notifyDataSetChanged()
        }
    }

    private fun setNewsListToAdapter(articles: List<Article>?) {
        newsAdapter.onNewsChanged(articles)
    }

    private fun handleLoaderVisibility(isLoading: Boolean?) {
        binding?.uiProgressBar?.visibility = if (isLoading == true) View.VISIBLE else View.GONE
    }

    private fun handleError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    private fun onSaveOrRemove(article: Article) {
        if (article.isSaved) {
            article.isSaved = false
            newsViewModel.removeItem(article)
        } else {
            article.isSaved = true
            newsViewModel.saveItem(article)
        }
    }

    private fun visitNewsWebsite(Article: Article) {
//        val newsWebsiteLink = Article.url
//        val bundle = Bundle()
//        bundle.putString("link", newsWebsiteLink)
//        val intent = Intent(this, NewsWebView::class.java)
//        intent.putExtras(bundle)
//        startActivity(intent)

        val urlLink = Article.url
        val builder = CustomTabsIntent.Builder()
        builder.setShowTitle(true)
        builder.setShareState(CustomTabsIntent.SHARE_STATE_ON)
        builder.setInstantAppsEnabled(true)
        val customBuilder = builder.build()
        if(this.isPackageInstalled(chromePackageName)){
            customBuilder.intent.setPackage(chromePackageName)
            customBuilder.launchUrl(this, Uri.parse(urlLink))
        }else{
            //open other available browser
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun upDate(activityResult: ActivityResult?) {
        if (activityResult?.resultCode == RESULT_OK) {
            newsViewModel.fetchAllNews()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menuitems, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.uiMiSaved -> {
                goToSavedNews()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToSavedNews() {
        val i = Intent(this, NewsSavedItemsActivity::class.java)
        upDateUi.launch(i)
    }

    private fun isPackageInstalled(packageName: String): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}
