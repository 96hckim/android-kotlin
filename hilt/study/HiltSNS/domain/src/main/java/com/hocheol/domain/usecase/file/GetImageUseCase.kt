package com.hocheol.domain.usecase.file

import com.hocheol.domain.model.Image

interface GetImageUseCase {

    operator fun invoke(contentUri: String): Image?
}