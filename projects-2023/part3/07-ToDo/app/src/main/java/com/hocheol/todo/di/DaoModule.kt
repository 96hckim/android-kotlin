package com.hocheol.todo.di

import com.hocheol.todo.data.AppDatabase
import com.hocheol.todo.data.dao.ContentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun providesContentDao(appDatabase: AppDatabase): ContentDao = appDatabase.contentDao()
}