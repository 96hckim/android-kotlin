package com.hocheol.movieapp.library.storage.di

import com.hocheol.movieapp.library.storage.IStorage
import com.hocheol.movieapp.library.storage.StorageManager
import com.hocheol.movieapp.library.storage.helpers.DataConverter
import com.hocheol.movieapp.library.storage.helpers.DataEncoding
import com.hocheol.movieapp.library.storage.prefs.SharedPrefsStorageProvider
import com.hocheol.movieapp.library.storage.prefs.StorageProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Singleton
    @Provides
    fun bindOnDiscStorage(
        storage: StorageProvider,
        converter: DataConverter,
        encoding: DataEncoding
    ): IStorage = StorageManager(storage, converter, encoding)

    @Provides
    fun provideSharePrefStorageProvider(provider: SharedPrefsStorageProvider): StorageProvider = provider
}