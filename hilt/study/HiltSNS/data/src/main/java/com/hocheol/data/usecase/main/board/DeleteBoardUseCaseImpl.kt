package com.hocheol.data.usecase.main.board

import com.hocheol.data.retrofit.BoardService
import com.hocheol.domain.usecase.main.board.DeleteBoardUseCase
import javax.inject.Inject

class DeleteBoardUseCaseImpl @Inject constructor(
    private val boardService: BoardService
) : DeleteBoardUseCase {

    override suspend fun invoke(boardId: Long): Result<Long> = runCatching {
        boardService.deleteBoard(boardId).data
    }
}