package com.hocheol.data.di

import android.content.Context
import androidx.room.Room
import com.hocheol.data.database.BoardDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    companion object {
        @Provides
        fun provideBoardDatabase(
            @ApplicationContext context: Context
        ): BoardDatabase = Room.databaseBuilder(
            context,
            BoardDatabase::class.java,
            "BoardDatabase"
        ).build()
    }
}