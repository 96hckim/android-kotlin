package com.hocheol.imageextraction.mvi

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.hocheol.imageextraction.R
import com.hocheol.imageextraction.databinding.ActivityMviBinding
import com.hocheol.imageextraction.mvi.repository.ImageRepositoryImpl
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MviActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMviBinding

    private val viewModel: MviViewModel by viewModels {
        MviViewModel.MviViewModelFactory(ImageRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMviBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            it.view = this
        }

        observeViewModel()
    }

    fun loadImage() {
        lifecycleScope.launch {
            viewModel.channel.send(MviIntent.LoadImage)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {
                    is MviState.Idle -> {
                        binding.progressBar.isVisible = false
                    }

                    is MviState.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is MviState.LoadedImage -> {
                        binding.progressBar.isVisible = false
                        binding.imageView.run {
                            setBackgroundColor(Color.parseColor(state.image.color))
                            load(state.image.url) {
                                crossfade(300)
                            }
                        }
                        binding.imageCountTextView.text = getString(R.string.loaded_image_count, state.count)
                    }
                }
            }
        }
    }
}