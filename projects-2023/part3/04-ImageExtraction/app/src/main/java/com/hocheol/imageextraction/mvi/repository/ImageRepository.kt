package com.hocheol.imageextraction.mvi.repository

import com.hocheol.imageextraction.mvi.model.Image

interface ImageRepository {

    suspend fun getRandomImage(): Image
}