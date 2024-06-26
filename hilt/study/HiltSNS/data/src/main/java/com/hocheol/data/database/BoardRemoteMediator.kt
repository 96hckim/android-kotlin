package com.hocheol.data.database

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.hocheol.data.model.BoardDTO
import com.hocheol.data.retrofit.BoardService
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class BoardRemoteMediator @Inject constructor(
    private val boardService: BoardService,
    private val boardDatabase: BoardDatabase
) : RemoteMediator<Int, BoardDTO>() {

    private val remoteKeyDao = boardDatabase.remoteKeyDao()
    private val boardDao = boardDatabase.boardDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, BoardDTO>): MediatorResult {
        val remoteKey = when (loadType) {
            LoadType.REFRESH -> {
                null
            }

            LoadType.PREPEND -> {
                return MediatorResult.Success(true)
            }

            LoadType.APPEND -> {
                remoteKeyDao.getNextKey()
            }
        }

        try {
            val page = remoteKey?.nextPage ?: 1
            val loadSize = 10

            val response = boardService.getBoards(
                page = page,
                size = loadSize
            )

            val boards = response.data

            boardDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.deleteAll()
                    boardDao.deleteAll()
                }

                remoteKeyDao.insertOrReplace(RemoteKey(nextPage = page + 1))
                boardDao.insertAll(boards)
            }

            return MediatorResult.Success(loadSize != boards.size)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}