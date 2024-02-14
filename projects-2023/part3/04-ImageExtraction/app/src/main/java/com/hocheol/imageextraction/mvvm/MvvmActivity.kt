package com.hocheol.imageextraction.mvvm

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hocheol.imageextraction.R
import com.hocheol.imageextraction.databinding.ActivityMvvmBinding
import com.hocheol.imageextraction.mvvm.repository.ImageRepositoryImpl

class MvvmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMvvmBinding

    private val viewModel: MvvmViewModel by viewModels {
        MvvmViewModel.MvvmViewModelFactory(ImageRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvvmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.view = this
        binding.viewModel = viewModel

        viewModel.countLiveData.observe(this) { count ->
            binding.imageCountTextView.text = getString(R.string.loaded_image_count, count)
        }
    }

    fun loadImage() {
        viewModel.loadRandomImage()
    }
}