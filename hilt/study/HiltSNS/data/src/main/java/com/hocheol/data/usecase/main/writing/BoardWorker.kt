package com.hocheol.data.usecase.main.writing

import android.app.Notification
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.hocheol.data.model.BoardParam
import com.hocheol.data.model.BoardParcel
import com.hocheol.data.model.ContentParam
import com.hocheol.data.retrofit.BoardService
import com.hocheol.data.service.PostingService
import com.hocheol.domain.model.ACTION_POSTED
import com.hocheol.domain.usecase.file.UploadImageUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.serialization.json.Json

@HiltWorker
class BoardWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted params: WorkerParameters,
    private val uploadImageUseCase: UploadImageUseCase,
    private val boardService: BoardService
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val boardParcelJson = inputData.getString(BoardParcel::class.java.simpleName) ?: return Result.failure()
        val boardParcel = Json.decodeFromString<BoardParcel>(boardParcelJson)
        postBoard(boardParcel)
        return Result.success()
    }

    private suspend fun postBoard(board: BoardParcel) {
        val uploadImages = board.images.mapNotNull { image ->
            uploadImageUseCase(image).getOrNull()
        }

        val contentParam = ContentParam(
            text = board.content,
            images = uploadImages
        )

        val boardParam = BoardParam(
            title = board.title,
            content = contentParam.toJson()
        )

        val requestBody = boardParam.toRequestBody()
        boardService.postBoard(requestBody)

        appContext.sendBroadcast(
            Intent(
                ACTION_POSTED
            ).apply {
                setPackage(appContext.packageName)
            }
        )
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val notification = createNotification()

        return ForegroundInfo(
            PostingService.FOREGROUND_NOTIFICATION_ID,
            notification
        )
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(appContext, PostingService.CHANNEL_ID).build()
    }
}