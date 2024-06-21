package com.hocheol.domain.usecase.main.writing

import com.hocheol.domain.model.Image

interface GetImageListUseCase {

    suspend operator fun invoke(): List<Image>
}