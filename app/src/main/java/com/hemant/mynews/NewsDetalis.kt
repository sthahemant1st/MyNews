package com.hemant.mynews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

class NewsDetalis : AppCompatActivity() {

    lateinit var webView : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detalis)

        webView = findViewById(R.id.webView)

        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled=true

        if (intent != null){
            if (!intent.getStringExtra("url")!!.isEmpty()){
                webView.loadUrl(intent.getStringExtra("url").toString())
            }

        }

    }

    override fun onBackPressed() {
        if (webView!!.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}