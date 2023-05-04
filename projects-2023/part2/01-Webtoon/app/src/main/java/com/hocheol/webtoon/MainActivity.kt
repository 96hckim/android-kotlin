package com.hocheol.webtoon

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.hocheol.webtoon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val currentFragment = supportFragmentManager.fragments[binding.viewPager.currentItem]
            if (currentFragment is WebViewFragment) {
                if (currentFragment.canGoBack()) {
                    currentFragment.goBack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            } else {
                isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            run {
//                val textView = TextView(this@MainActivity).apply {
//                    text = "position $position"
//                    gravity = Gravity.CENTER
//                }
//                tab.customView = textView
                tab.text = "position $position"
            }
        }.attach()

        onBackPressedDispatcher.addCallback(this, callback)
    }
}