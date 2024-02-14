package com.hocheol.imageextraction.mvc

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.hocheol.imageextraction.R
import com.hocheol.imageextraction.databinding.ActivityMvcBinding
import com.hocheol.imageextraction.mvc.provider.ImageProvider

class MvcActivity : AppCompatActivity(), ImageProvider.Callback {

    private lateinit var binding: ActivityMvcBinding

    private val model = ImageCountModel()
    private val imageProvider = ImageProvider(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvcBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.view = this
    }

    fun loadImage() {
        imageProvider.getRandomImage()
    }

    override fun loadImage(url: String, color: String) {
        model.increase()
        with(binding) {
            imageView.run {
                setBackgroundColor(Color.parseColor(color))
                load(url)
            }
            imageCountTextView.text = getString(R.string.loaded_image_count, model.count)
        }
    }
}