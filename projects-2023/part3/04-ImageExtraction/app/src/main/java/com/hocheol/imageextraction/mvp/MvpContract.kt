package com.hocheol.imageextraction.mvp

interface MvpContract {

    interface View {

        fun showImage(url: String, color: String)

        fun showImageCountText(count: Int)

    }

    interface Presenter {

        fun attachView(view: View)

        fun detachView()

        fun loadRandomImage()

    }
}