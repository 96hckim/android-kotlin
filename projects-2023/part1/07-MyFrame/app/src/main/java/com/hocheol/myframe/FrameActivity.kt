package com.hocheol.myframe

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.hocheol.myframe.databinding.ActivityFrameBinding

class FrameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFrameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.apply {
            title = "나만의 앨범"
            setSupportActionBar(this)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val images = (intent.getStringArrayExtra("images") ?: emptyArray()).map { uriString ->
            FrameItem(Uri.parse(uriString))
        }
        val frameAdapter = FrameAdapter(images)

        binding.imageViewPager.adapter = frameAdapter

        TabLayoutMediator(
            binding.tabLayout,
            binding.imageViewPager
        ) { tab, position ->
            binding.imageViewPager.currentItem = tab.position
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}