package com.hocheol.data.di

import com.hocheol.data.usecase.main.board.DeleteBoardUseCaseImpl
import com.hocheol.data.usecase.main.board.GetBoardsUseCaseImpl
import com.hocheol.domain.usecase.main.board.DeleteBoardUseCase
import com.hocheol.domain.usecase.main.board.GetBoardsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class BoardModule {

    @Binds
    abstract fun bindGetBoardsUseCase(getBoardsUseCaseImpl: GetBoardsUseCaseImpl): GetBoardsUseCase

    @Binds
    abstract fun bindDeleteBoardUseCase(deleteBoardUseCaseImpl: DeleteBoardUseCaseImpl): DeleteBoardUseCase
}