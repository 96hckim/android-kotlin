package com.hocheol.bookreview

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hocheol.bookreview.dao.HistoryDao
import com.hocheol.bookreview.model.History

@Database(entities = [History::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}