package com.hocheol.data.di

import android.content.Context
import androidx.room.Room
import com.hocheol.data.db.ApplicationDatabase
import com.hocheol.data.db.dao.BasketDao
import com.hocheol.data.db.dao.LikeDao
import com.hocheol.data.db.dao.PurchaseHistoryDao
import com.hocheol.data.db.dao.SearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FakeDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ApplicationDatabase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            klass = ApplicationDatabase::class.java,
            name = ApplicationDatabase.DB_NAME
        )
            .fallbackToDestructiveMigration()
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

    @Provides
    @Singleton
    fun provideBasketDao(database: ApplicationDatabase): BasketDao {
        return database.basketDao()
    }

    @Provides
    @Singleton
    fun providePurchaseHistoryDao(database: ApplicationDatabase): PurchaseHistoryDao {
        return database.purchaseHistoryDao()
    }
}