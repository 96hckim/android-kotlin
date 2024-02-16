package com.hocheol.shoppingmall.di

import com.hocheol.shoppingmall.remote.MainService
import com.hocheol.shoppingmall.remote.repository.MainRepository
import com.hocheol.shoppingmall.remote.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MainRepositoryModule {

    @Provides
    @ViewModelScoped
    fun providesMainRepository(
        mainService: MainService
    ): MainRepository = MainRepositoryImpl(mainService)
}