package com.hocheol.newsapp

import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hocheol.newsapp.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra("url")

        if (url.isNullOrEmpty()) {
            Toast.makeText(this, "잘못된 URL 입니다.", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            binding.newsWebView.webViewClient = WebViewClient()
            binding.newsWebView.settings.javaScriptEnabled = true

            binding.newsWebView.loadUrl(url)
        }
    }
}