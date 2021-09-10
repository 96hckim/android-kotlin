package com.hocheol.bookreview

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hocheol.bookreview.dao.HistoryDao
import com.hocheol.bookreview.dao.ReviewDao
import com.hocheol.bookreview.model.History
import com.hocheol.bookreview.model.Review

@Database(entities = [History::class, Review::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun reviewDao(): ReviewDao
}

fun getAppDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "BookDB"
    ).build()
}