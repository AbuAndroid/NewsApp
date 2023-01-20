package com.example.viewmodellivedata.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import com.example.viewmodellivedata.databinding.ActivityNewsWebViewBinding

class NewsWebView : AppCompatActivity() {
    private var binding: ActivityNewsWebViewBinding? = null
    private var urlLink: String? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsWebViewBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setUpUi()
        setUrlToWebView()
    }

    override fun onBackPressed() {
        if (binding?.uiNewsWebView?.canGoBack() == true)
            binding?.uiNewsWebView?.goBack()
        else
            super.onBackPressed()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpUi() {
        binding?.uiNewsWebView?.webViewClient = WebViewClient()
        binding?.uiNewsWebView?.settings?.javaScriptEnabled = true
        binding?.uiNewsWebView?.settings?.setSupportZoom(true)
    }

    private fun setUrlToWebView() {
        val bundle = intent.extras
        urlLink = bundle?.getString("link", null)
        urlLink?.let { binding?.uiNewsWebView?.loadUrl(it) }
    }

    inner class WebViewClient : android.webkit.WebViewClient() {
        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return false
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            binding?.uiPvLoadWebView?.visibility = View.GONE
        }
    }
}