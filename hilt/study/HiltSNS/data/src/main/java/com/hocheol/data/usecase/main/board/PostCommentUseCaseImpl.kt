package com.hocheol.data.usecase.main.board

import com.hocheol.data.model.comment.CommentParam
import com.hocheol.data.retrofit.BoardService
import com.hocheol.domain.usecase.main.board.PostCommentUseCase
import javax.inject.Inject

class PostCommentUseCaseImpl @Inject constructor(
    private val boardService: BoardService
) : PostCommentUseCase {

    override suspend fun invoke(boardId: Long, text: String): Result<Long> = runCatching {
        val requestBody = CommentParam(text).toRequestBody()
        boardService.postComment(
            boardId = boardId,
            requestBody = requestBody
        ).data
    }
}