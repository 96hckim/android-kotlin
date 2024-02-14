package com.hocheol.imageextraction.mvp

import com.hocheol.imageextraction.mvp.model.ImageCountModel
import com.hocheol.imageextraction.mvp.repository.ImageRepository

class MvpPresenter(
    private val model: ImageCountModel,
    private val imageRepository: ImageRepository
) : MvpContract.Presenter, ImageRepository.Callback {

    private var view: MvpContract.View? = null

    override fun attachView(view: MvpContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun loadRandomImage() {
        imageRepository.getRandomImage(this)
    }

    override fun loadImage(url: String, color: String) {
        val v = view ?: return

        model.increase()
        v.showImage(url, color)
        v.showImageCountText(model.count)
    }
}