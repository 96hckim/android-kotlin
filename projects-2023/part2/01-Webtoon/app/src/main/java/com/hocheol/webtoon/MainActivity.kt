package com.hocheol.webtoon

import android.content.Context
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.hocheol.webtoon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnTabLayoutNameChanged {
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

        val sharedPreferences = getSharedPreferences(WebViewFragment.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val tab0 = sharedPreferences?.getString("tab0_name", "Webtoon 0")
        val tab1 = sharedPreferences?.getString("tab1_name", "Webtoon 1")
        val tab2 = sharedPreferences?.getString("tab2_name", "Webtoon 2")

        binding.viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            run {
//                val textView = TextView(this@MainActivity).apply {
//                    text = "Webtoon $position"
//                    gravity = Gravity.CENTER
//                }
//                tab.customView = textView
                tab.text = when (position) {
                    0 -> tab0
                    1 -> tab1
                    else -> tab2
                }
            }
        }.attach()

        onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun nameChanged(position: Int, name: String) {
        val tab = binding.tabLayout.getTabAt(position)
        tab?.text = name
    }
}