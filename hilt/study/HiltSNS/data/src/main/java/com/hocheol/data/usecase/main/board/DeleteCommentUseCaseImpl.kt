package com.hocheol.data.usecase.main.board

import com.hocheol.data.retrofit.BoardService
import com.hocheol.domain.usecase.main.board.DeleteCommentUseCase
import javax.inject.Inject

class DeleteCommentUseCaseImpl @Inject constructor(
    private val boardService: BoardService
) : DeleteCommentUseCase {

    override suspend fun invoke(boardId: Long, commentId: Long): Result<Long> = runCatching {
        boardService.deleteComment(
            boardId = boardId,
            commentId = commentId
        ).data
    }
}