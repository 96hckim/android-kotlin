package com.hocheol.delivery.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hocheol.delivery.data.db.dao.LocationDao
import com.hocheol.delivery.data.entity.LocationLatLngEntity

@Database(
    entities = [LocationLatLngEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun LocationDao(): LocationDao

    companion object {
        const val DB_NAME = "ApplicationDatabase.db"
    }

}