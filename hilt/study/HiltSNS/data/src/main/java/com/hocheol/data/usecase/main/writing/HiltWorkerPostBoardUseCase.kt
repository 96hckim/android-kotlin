package com.hocheol.data.usecase.main.writing

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.hocheol.data.model.BoardParcel
import com.hocheol.domain.model.Image
import com.hocheol.domain.usecase.main.writing.PostBoardUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class HiltWorkerPostBoardUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : PostBoardUseCase {

    override suspend fun invoke(title: String, content: String, images: List<Image>) {
        val boardParcel = BoardParcel(
            title = title,
            content = content,
            images = images
        )

        val boardParcelJson = Json.encodeToString(boardParcel)

        val workRequest = OneTimeWorkRequestBuilder<BoardWorker>()
            .setInputData(
                workDataOf(
                    BoardParcel::class.java.simpleName to boardParcelJson
                )
            )
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}

