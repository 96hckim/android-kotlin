package com.hocheol.data.usecase.main.board

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.hocheol.data.database.BoardDatabase
import com.hocheol.data.database.BoardRemoteMediator
import com.hocheol.data.model.toDomainModel
import com.hocheol.domain.model.Board
import com.hocheol.domain.usecase.main.board.GetBoardsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBoardsUseCaseImpl @Inject constructor(
    private val boardDatabase: BoardDatabase,
    private val boardRemoteMediator: BoardRemoteMediator
) : GetBoardsUseCase {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun invoke(): Result<Flow<PagingData<Board>>> = runCatching {
        Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10
            ),
            remoteMediator = boardRemoteMediator,
            pagingSourceFactory = { boardDatabase.boardDao().getAll() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomainModel() }
        }
    }
}