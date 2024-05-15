package com.hocheol.data.usecase.file

import com.hocheol.data.di.SERVER_HOST
import com.hocheol.data.retrofit.FileService
import com.hocheol.data.retrofit.UriRequestBody
import com.hocheol.domain.model.Image
import com.hocheol.domain.usecase.file.GetInputStreamUseCase
import com.hocheol.domain.usecase.file.UploadImageUseCase
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadImageUseCaseImpl @Inject constructor(
    private val fileService: FileService,
    private val getInputStreamUseCase: GetInputStreamUseCase
) : UploadImageUseCase {

    override suspend fun invoke(image: Image): Result<String> = runCatching {
        val fileNamePart = MultipartBody.Part.createFormData(
            name = "fileName",
            value = image.name
        )

        val requestBody = UriRequestBody(
            contentUri = image.uri,
            contentType = image.mimeType.toMediaType(),
            contentLength = image.size,
            getInputStreamUseCase = getInputStreamUseCase
        )

        val filePart = MultipartBody.Part.createFormData(
            name = "file",
            filename = image.name,
            body = requestBody
        )

        val fileDTO = fileService.uploadFile(
            fileName = fileNamePart,
            file = filePart
        ).data

        return@runCatching "$SERVER_HOST/${fileDTO.filePath}"
    }
}