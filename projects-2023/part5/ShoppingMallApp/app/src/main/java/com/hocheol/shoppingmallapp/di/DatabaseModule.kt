package com.hocheol.shoppingmallapp.di

import android.content.Context
import androidx.room.Room
import com.hocheol.data.db.ApplicationDatabase
import com.hocheol.data.db.dao.LikeDao
import com.hocheol.data.db.dao.SearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ApplicationDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ApplicationDatabase::class.java,
            ApplicationDatabase.DB_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideSearchDao(database: ApplicationDatabase): SearchDao {
        return database.searchDao()
    }

    @Provides
    @Singleton
    fun provideLikeDao(database: ApplicationDatabase): LikeDao {
        return database.likeDao()
    }
}