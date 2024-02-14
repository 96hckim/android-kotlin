package com.hocheol.imageextraction.mvp.repository

interface ImageRepository {

    fun getRandomImage(callback: Callback)

    interface Callback {
        fun loadImage(url: String, color: String)
    }
}