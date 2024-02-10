package com.hocheol.youtube

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.hocheol.youtube.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var videoAdapter: VideoAdapter

    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initMotionLayout()
        initVideoRecyclerView()
        initPlayerRecyclerView()
    }

    private fun initMotionLayout() {
        binding.motionLayout.targetView = binding.videoPlayerContainer
        binding.motionLayout.jumpToState(R.id.collapse)
    }

    private fun initVideoRecyclerView() {
        val videos = readData("videos.json", Videos::class.java) ?: Videos(videos = emptyList())

        videoAdapter = VideoAdapter(context = this) { video ->
            binding.motionLayout.setTransition(R.id.collapse, R.id.expand)
            binding.motionLayout.transitionToEnd()

            play(video)
        }.apply {
            submitList(videos.videos)
        }

        binding.videoRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = videoAdapter
        }
    }

    private fun initPlayerRecyclerView() {
        binding.playerRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter
        }
    }

    private fun initExoPlayer() {
        player = ExoPlayer.Builder(this).build()
            .also {
                binding.playerView.player = it
            }
    }

    private fun play(video: Video) {
        player?.let {
            it.setMediaItem(MediaItem.fromUri(Uri.parse(video.videoUrl)))
            it.prepare()
            it.play()
        }
    }

    override fun onStart() {
        super.onStart()

        if (player == null) {
            initExoPlayer()
        }
    }

    override fun onResume() {
        super.onResume()

        if (player == null) {
            initExoPlayer()
        }
    }

    override fun onStop() {
        super.onStop()

        player?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()

        player?.release()
    }
}