package com.hocheol.data.usecase.file

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.hocheol.domain.model.Image
import com.hocheol.domain.usecase.file.GetImageUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetImageUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : GetImageUseCase {

    override fun invoke(contentUri: String): Image? {
        val uri = Uri.parse(contentUri)
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.MIME_TYPE
        )

        val cursor = context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )

        return cursor?.use { c ->
            c.moveToNext()

            val idIndex = c.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val displayNameIndex = c.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val sizeIndex = c.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
            val mimeTypeIndex = c.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)

            val displayName = c.getString(displayNameIndex)
            val size = c.getLong(sizeIndex)
            val mimeType = c.getString(mimeTypeIndex)

            Image(
                uri = contentUri,
                name = displayName,
                size = size,
                mimeType = mimeType
            )
        }
    }
}