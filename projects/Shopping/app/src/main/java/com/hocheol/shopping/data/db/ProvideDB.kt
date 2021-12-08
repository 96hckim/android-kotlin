package com.hocheol.shopping.data.db

import android.content.Context
import androidx.room.Room

internal fun provideDB(context: Context): ProductDatabase =
    Room.databaseBuilder(context, ProductDatabase::class.java, ProductDatabase.DB_NAME).build()

internal fun provideProductDao(database: ProductDatabase) = database.productDao()
