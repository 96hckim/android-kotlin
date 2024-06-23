package com.hocheol.domain.usecase.main.writing

import com.hocheol.domain.model.Image

interface PostBoardUseCase {

    suspend operator fun invoke(
        title: String,
        content: String,
        images: List<Image>
    )
}