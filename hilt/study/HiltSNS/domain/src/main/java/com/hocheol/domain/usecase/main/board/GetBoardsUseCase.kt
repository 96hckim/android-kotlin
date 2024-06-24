package com.hocheol.domain.usecase.main.board

import androidx.paging.PagingData
import com.hocheol.domain.model.Board
import kotlinx.coroutines.flow.Flow

interface GetBoardsUseCase {

    suspend operator fun invoke(): Result<Flow<PagingData<Board>>>
}