package com.example.viewmodellivedata.ui

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import com.example.viewmodellivedata.databinding.ActivityNewsWebViewBinding

class NewsWebView : AppCompatActivity() {
    private var binding: ActivityNewsWebViewBinding? = null
    private var urlLink: String? = null
    private var chromePackageName = "com.android.chrome"
    val mDelay: Long = 5000
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsWebViewBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setUrlToWebView()
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
        if(this.isPackageInstalled(chromePackageName)){
            customBuilder.intent.setPackage(chromePackageName)
            customBuilder.launchUrl(this, Uri.parse(urlLink))
        }else{
            //open other available browsers
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
}