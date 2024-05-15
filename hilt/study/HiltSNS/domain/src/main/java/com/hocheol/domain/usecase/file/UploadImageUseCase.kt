package com.hocheol.domain.usecase.file

import com.hocheol.domain.model.Image

interface UploadImageUseCase {

    suspend operator fun invoke(image: Image): Result<String>
}