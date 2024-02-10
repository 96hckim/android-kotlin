package com.hocheol.youtube

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
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
        initControlButton()
        initHideButton()
        initPlayerRecyclerView()
    }

    private fun initMotionLayout() {
        binding.motionLayout.targetView = binding.videoPlayerContainer
        binding.motionLayout.jumpToState(R.id.hide)

        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {
            }

            override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {
                binding.playerView.useController = false
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                binding.playerView.useController = currentId == R.id.expand
            }

            override fun onTransitionTrigger(motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) {
            }
        })
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

    private fun initControlButton() {
        binding.controlButton.setOnClickListener {
            player?.let {
                if (it.isPlaying) {
                    it.pause()
                } else {
                    it.play()
                }
            }
        }
    }

    private fun initHideButton() {
        binding.hideButton.setOnClickListener {
            binding.motionLayout.transitionToState(R.id.hide)
            player?.pause()
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
                binding.playerView.useController = false

                it.addListener(object : Player.Listener {
                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        super.onIsPlayingChanged(isPlaying)

                        if (isPlaying) {
                            binding.controlButton.setImageResource(R.drawable.baseline_pause_24)
                        } else {
                            binding.controlButton.setImageResource(R.drawable.baseline_play_arrow_24)
                        }
                    }
                })
            }
    }

    private fun play(video: Video) {
        player?.let {
            it.setMediaItem(MediaItem.fromUri(Uri.parse(video.videoUrl)))
            it.prepare()
            it.play()
        }

        binding.videoTitleTextView.text = video.title
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