package com.hocheol.data.usecase.main.board

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hocheol.domain.model.Board
import com.hocheol.domain.usecase.main.board.GetBoardsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Provider

class GetBoardsUseCaseImpl @Inject constructor(
    private val pagingSource: Provider<BoardPagingSource>
) : GetBoardsUseCase {

    override suspend fun invoke(): Result<Flow<PagingData<Board>>> = runCatching {
        Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { pagingSource.get() }
        ).flow
    }
}