package com.hocheol.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hocheol.todo.data.dao.ContentDao
import com.hocheol.todo.model.ContentEntity

@Database(entities = [ContentEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contentDao(): ContentDao
}