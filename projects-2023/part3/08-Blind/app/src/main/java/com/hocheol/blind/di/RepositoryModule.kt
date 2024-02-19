package com.hocheol.blind.di

import com.hocheol.blind.data.repository.ContentRepositoryImpl
import com.hocheol.blind.data.source.local.dao.ContentDao
import com.hocheol.blind.data.source.remote.api.ContentService
import com.hocheol.blind.domain.repository.ContentRepository
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
    fun providesContentRepository(
        contentService: ContentService,
        contentDao: ContentDao
    ): ContentRepository = ContentRepositoryImpl(contentService, contentDao)
}