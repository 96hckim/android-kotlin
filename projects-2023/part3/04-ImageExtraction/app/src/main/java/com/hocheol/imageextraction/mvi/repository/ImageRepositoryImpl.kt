package com.hocheol.imageextraction.mvi.repository

import com.hocheol.imageextraction.RetrofitManager
import com.hocheol.imageextraction.mvi.model.Image
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageRepositoryImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ImageRepository {

    override suspend fun getRandomImage(): Image = withContext(dispatcher) {
        RetrofitManager.imageService.getRandomImageSuspend().let {
            Image(it.urls.regular, it.color)
        }
    }
}