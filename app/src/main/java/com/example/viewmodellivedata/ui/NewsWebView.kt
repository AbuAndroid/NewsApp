package com.example.viewmodellivedata.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.trusted.TokenStore
import com.example.viewmodellivedata.databinding.ActivityNewsWebViewBinding

class NewsWebView : AppCompatActivity() {
    private var binding: ActivityNewsWebViewBinding? = null
    private var urlLink: String? = null
    private var package_name = "com.android.chrome";
    val mDelay: Long = 5000
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsWebViewBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setUpUi()
        setUrlToWebView()


//        Handler(Looper.myLooper()!!).postDelayed({
//            Toast.makeText(this,"working..",Toast.LENGTH_SHORT).show()
//        },mDelay)
    }



    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpUi() {
//        binding?.uiNewsWebView?.webViewClient = WebViewClient()

    }

    private fun setUrlToWebView() {
        val bundle = intent.extras
        urlLink = bundle?.getString("link", null)
        urlLink?.let { binding?.uiNewsWebView?.loadUrl(it) }
        val builder = CustomTabsIntent.Builder()
        builder.setShowTitle(true)
        builder.setShareState(CustomTabsIntent.SHARE_STATE_ON)
        builder.setInstantAppsEnabled(true)
        val customBuilder = builder.build()
        if(this.isPackageInstalled(package_name)){
            customBuilder.intent.setPackage(package_name)
            customBuilder.launchUrl(this, Uri.parse(urlLink))
        }else{

        }

        object : CountDownTimer(mDelay, 0) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {

            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
            }
        }.start()

    }
    private fun isPackageInstalled(packageName: String): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
//    inner class WebViewClient : android.webkit.WebViewClient() {
//        @Deprecated("Deprecated in Java")
//        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
//            view.loadUrl(url)
//            return false
//        }
//
//        override fun onPageFinished(view: WebView, url: String) {
//            super.onPageFinished(view, url)
//            binding?.uiPvLoadWebView?.visibility = View.GONE
//        }
//    }
}