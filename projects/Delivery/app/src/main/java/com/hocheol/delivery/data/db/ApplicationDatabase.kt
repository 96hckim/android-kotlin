package com.hocheol.delivery.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hocheol.delivery.data.db.dao.LocationDao
import com.hocheol.delivery.data.db.dao.RestaurantDao
import com.hocheol.delivery.data.entity.LocationLatLngEntity
import com.hocheol.delivery.data.entity.RestaurantEntity

@Database(
    entities = [LocationLatLngEntity::class, RestaurantEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun LocationDao(): LocationDao

    abstract fun RestaurantDao(): RestaurantDao

    companion object {
        const val DB_NAME = "ApplicationDatabase.db"
    }

}