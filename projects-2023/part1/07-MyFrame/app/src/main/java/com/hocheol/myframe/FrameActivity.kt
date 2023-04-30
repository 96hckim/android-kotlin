package com.hocheol.myframe

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hocheol.myframe.databinding.ActivityFrameBinding

class FrameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFrameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val images = (intent.getStringArrayExtra("images") ?: emptyArray()).map { uriString ->
            FrameItem(Uri.parse(uriString))
        }
        val frameAdapter = FrameAdapter(images)

        binding.imageViewPager.adapter = frameAdapter
    }
}