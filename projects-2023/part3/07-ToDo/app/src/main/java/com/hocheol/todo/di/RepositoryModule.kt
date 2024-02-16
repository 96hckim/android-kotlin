package com.hocheol.todo.di

import com.hocheol.todo.data.dao.ContentDao
import com.hocheol.todo.repository.ContentRepository
import com.hocheol.todo.repository.ContentRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun providesContentRepository(contentDao: ContentDao): ContentRepository = ContentRepositoryImpl(contentDao)
}