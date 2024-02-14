package com.hocheol.imageextraction.mvvm.repository

import com.hocheol.imageextraction.mvvm.model.Image
import io.reactivex.rxjava3.core.Single

interface ImageRepository {

    fun getRandomImage(): Single<Image>
}