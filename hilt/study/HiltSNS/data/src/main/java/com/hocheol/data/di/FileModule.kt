package com.hocheol.data.di

import com.hocheol.data.usecase.file.GetImageUseCaseImpl
import com.hocheol.data.usecase.file.GetInputStreamUseCaseImpl
import com.hocheol.data.usecase.file.UploadImageUseCaseImpl
import com.hocheol.domain.usecase.file.GetImageUseCase
import com.hocheol.domain.usecase.file.GetInputStreamUseCase
import com.hocheol.domain.usecase.file.UploadImageUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FileModule {

    @Binds
    abstract fun bindGetInputStreamUseCase(getInputStreamUseCaseImpl: GetInputStreamUseCaseImpl): GetInputStreamUseCase

    @Binds
    abstract fun bindGetImageUseCase(getImageUseCaseImpl: GetImageUseCaseImpl): GetImageUseCase

    @Binds
    abstract fun bindUploadImageUseCase(uploadImageUseCaseImpl: UploadImageUseCaseImpl): UploadImageUseCase
}