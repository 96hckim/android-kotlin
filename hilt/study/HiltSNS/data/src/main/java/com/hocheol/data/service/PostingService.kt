package com.hocheol.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.IntentCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.hocheol.data.model.BoardParam
import com.hocheol.data.model.BoardParcel
import com.hocheol.data.model.ContentParam
import com.hocheol.data.retrofit.BoardService
import com.hocheol.domain.usecase.file.UploadImageUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PostingService : LifecycleService() {

    @Inject
    lateinit var uploadImageUseCase: UploadImageUseCase

    @Inject
    lateinit var boardService: BoardService

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        startForeground()

        intent?.run {
            if (hasExtra(EXTRA_BOARD)) {
                val boardParcel = IntentCompat.getParcelableExtra(this, EXTRA_BOARD, BoardParcel::class.java)
                boardParcel?.let { board ->
                    lifecycleScope.launch(Dispatchers.IO) {
                        postBoard(board)
                    }
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "백그라운드에서 글을 업로드 합니다."

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    private fun startForeground() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID).build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                FOREGROUND_NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SHORT_SERVICE
            )
        } else {
            startForeground(
                FOREGROUND_NOTIFICATION_ID,
                notification
            )
        }
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

        stopForeground(STOP_FOREGROUND_DETACH)
    }

    companion object {
        const val EXTRA_BOARD = "extra_board"
        const val CHANNEL_ID = "게시글 업로드"
        const val CHANNEL_NAME = "게시글 업로드 채널"
        const val FOREGROUND_NOTIFICATION_ID = 1000
    }
}