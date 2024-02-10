package com.hocheol.youtube

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hocheol.youtube.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var videoAdapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val videos = readData("videos.json", Videos::class.java) ?: Videos(videos = emptyList())

        videoAdapter = VideoAdapter(context = this) { video ->
            binding.motionLayout.setTransition(R.id.collapse, R.id.expand)
            binding.motionLayout.transitionToEnd()
        }.apply {
            submitList(videos.videos)
        }

        binding.motionLayout.jumpToState(R.id.collapse)

        binding.videoRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = videoAdapter
        }

        binding.playerRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter
        }
    }
}