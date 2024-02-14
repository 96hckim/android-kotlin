package com.hocheol.imageextraction.mvp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.hocheol.imageextraction.R
import com.hocheol.imageextraction.databinding.ActivityMvpBinding
import com.hocheol.imageextraction.mvp.model.ImageCountModel
import com.hocheol.imageextraction.mvp.repository.ImageRepositoryImpl

class MvpActivity : AppCompatActivity(), MvpContract.View {

    private lateinit var binding: ActivityMvpBinding

    private lateinit var presenter: MvpContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.view = this

        presenter = MvpPresenter(
            model = ImageCountModel(),
            imageRepository = ImageRepositoryImpl()
        )
        presenter.attachView(this)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    fun loadImage() {
        presenter.loadRandomImage()
    }

    override fun showImage(url: String, color: String) {
        binding.imageView.run {
            setBackgroundColor(Color.parseColor(color))
            load(url) {
                crossfade(300)
            }
        }
    }

    override fun showImageCountText(count: Int) {
        binding.imageCountTextView.text = getString(R.string.loaded_image_count, count)
    }
}