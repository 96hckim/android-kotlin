package com.hocheol.data.usecase.main.writing

import android.content.Context
import android.content.Intent
import com.hocheol.data.model.BoardParcel
import com.hocheol.data.service.PostingService
import com.hocheol.domain.model.Image
import com.hocheol.domain.usecase.main.writing.PostBoardUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PostBoardUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PostBoardUseCase {

    override suspend fun invoke(title: String, content: String, images: List<Image>) {
        val board = BoardParcel(title = title, content = content, images = images)

        context.startForegroundService(
            Intent(
                context,
                PostingService::class.java
            ).apply {
                putExtra(PostingService.EXTRA_BOARD, board)
            }
        )
    }
}