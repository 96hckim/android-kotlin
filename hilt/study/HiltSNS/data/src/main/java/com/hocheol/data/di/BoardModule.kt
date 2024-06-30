package com.hocheol.data.di

import com.hocheol.data.usecase.main.board.DeleteBoardUseCaseImpl
import com.hocheol.data.usecase.main.board.DeleteCommentUseCaseImpl
import com.hocheol.data.usecase.main.board.PagingSourceGetBoardsUseCase
import com.hocheol.data.usecase.main.board.PostCommentUseCaseImpl
import com.hocheol.domain.usecase.main.board.DeleteBoardUseCase
import com.hocheol.domain.usecase.main.board.DeleteCommentUseCase
import com.hocheol.domain.usecase.main.board.GetBoardsUseCase
import com.hocheol.domain.usecase.main.board.PostCommentUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class BoardModule {

//    @Binds
//    abstract fun bindGetBoardsUseCase(getBoardsUseCaseImpl: GetBoardsUseCaseImpl): GetBoardsUseCase

    @Binds
    abstract fun bindPagingSourceGetBoardsUseCase(pagingSourceGetBoardsUseCase: PagingSourceGetBoardsUseCase): GetBoardsUseCase

    @Binds
    abstract fun bindDeleteBoardUseCase(deleteBoardUseCaseImpl: DeleteBoardUseCaseImpl): DeleteBoardUseCase

    @Binds
    abstract fun bindPostCommentUseCase(postCommentUseCaseImpl: PostCommentUseCaseImpl): PostCommentUseCase

    @Binds
    abstract fun bindDeleteCommentUseCase(deleteCommentUseCaseImpl: DeleteCommentUseCaseImpl): DeleteCommentUseCase
}