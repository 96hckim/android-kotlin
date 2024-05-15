package com.hocheol.data.usecase.file

import android.content.Context
import android.net.Uri
import com.hocheol.domain.usecase.file.GetInputStreamUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import javax.inject.Inject

class GetInputStreamUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : GetInputStreamUseCase {

    override fun invoke(contentUri: String): Result<InputStream> = runCatching {
        val uri = Uri.parse(contentUri)
        context.contentResolver.openInputStream(uri) ?: throw IllegalStateException("InputStream 얻기 실패")
    }
}