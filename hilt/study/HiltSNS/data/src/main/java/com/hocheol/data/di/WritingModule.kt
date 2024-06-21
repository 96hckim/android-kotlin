package com.hocheol.data.di

import com.hocheol.data.usecase.main.writing.GetImageListUseCaseImpl
import com.hocheol.domain.usecase.main.writing.GetImageListUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class WritingModule {

    @Binds
    abstract fun bindGetImageListUseCase(getImageListUseCaseImpl: GetImageListUseCaseImpl): GetImageListUseCase
}