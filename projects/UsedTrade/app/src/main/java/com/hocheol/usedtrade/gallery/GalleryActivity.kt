package com.hocheol.usedtrade.gallery

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.hocheol.usedtrade.R
import com.hocheol.usedtrade.databinding.ActivityGalleryBinding
import com.hocheol.usedtrade.photo.ImageListActivity

class GalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding

    private val viewModel by viewModels<GalleryViewModel>()

    private val adapter = GalleryPhotoListAdapter {
        viewModel.selectPhoto(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.fetchData()
        initViews()
        observeState()
    }

    private fun initViews() = with(binding) {
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(GridDividerDecoration(this@GalleryActivity, R.drawable.bg_frame_gallery))

        confirmButton.setOnClickListener {
            viewModel.confirmCheckedPhotos()
        }
    }

    private fun handleLoading() = with(binding) {
        progressBar.isVisible = true
        recyclerView.isGone = true
    }

    private fun handleSuccess(state: GalleryState.Success) = with(binding) {
        progressBar.isGone = true
        recyclerView.isVisible = true
        adapter.setPhotoList(state.photoList)
    }

    private fun handleConfirm(state: GalleryState.Confirm) {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(ImageListActivity.URI_LIST_KEY, ArrayList(state.photoList.map { it.uri }))
        })
        finish()
    }

    private fun observeState() = viewModel.galleryStateLiveData.observe(this) {
        when (it) {
            is GalleryState.Loading -> handleLoading()
            is GalleryState.Success -> handleSuccess(it)
            is GalleryState.Confirm -> handleConfirm(it)
            else -> Unit
        }
    }

}