package com.hocheol.calculator

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hocheol.calculator.dao.HistoryDao
import com.hocheol.calculator.model.History

@Database(entities = [History::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

}