package com.example.viewmodellivedata.ui

import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.viewmodellivedata.adapter.NewsAdapter
import com.example.viewmodellivedata.databinding.ActivityNewsSavedItemsBinding
import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsSavedItemsActivity : AppCompatActivity() {

    private var binding: ActivityNewsSavedItemsBinding? = null
    private val newsViewModel: NewsViewModel by viewModel()
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(
            allNewsList = mutableListOf(),
            onLinkClicked = this::viewWebsite,
            onSaveOrRemove = this::onSaveOrRemove
        )
    }
    private var chromePackageName = "com.android.chrome"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsSavedItemsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setUpUi()
        getNewsListFromViewModel()
    }

    private fun setUpUi() {
        binding?.uiRvSavedlist?.adapter = newsAdapter
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deleteItem: Article =
                    newsViewModel.fetchSavedNews()[viewHolder.adapterPosition] //courseList[viewHolder.adapterPosition]
                val position = viewHolder.adapterPosition
                onSaveOrRemove(deleteItem)
                newsAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                Snackbar.make(
                    binding!!.uiRvSavedlist,
                    "Deleted " + deleteItem.title,
                    Snackbar.LENGTH_LONG
                ).setAction(
                        "Undo"
                    ) {
                        saveItemToSpecificPosition(deleteItem, position)
                        newsAdapter.notifyItemInserted(position)
                        getNewsListFromViewModel()
                    }.show()
            }
        }).attachToRecyclerView(binding!!.uiRvSavedlist)
    }

    private fun getNewsListFromViewModel() {
        displayListToAdapter(newsViewModel.fetchSavedNews())
    }

    private fun displayListToAdapter(articles: List<Article>) {
        newsAdapter.onNewsChanged(articles)
    }

    private fun onSaveOrRemove(article: Article) {
        newsViewModel.removeItem(article)
        getNewsListFromViewModel()
    }

    private fun saveItemToSpecificPosition(article: Article, position: Int) {
        article.isSaved = true
        newsViewModel.saveSpecificPosition(article, position)
    }

    private fun viewWebsite(Article: Article) {
//        val link = Article.url
//        val bundle = Bundle()
//        bundle.putString("link", link)
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
            //open link in other available browser
        }
    }

    private fun isPackageInstalled(packageName: String): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        Log.e("flg", newsViewModel.flag.toString())
        if (newsViewModel.flag) setResult(RESULT_OK)
        else setResult(RESULT_CANCELED)
        super.onBackPressed()
    }
}