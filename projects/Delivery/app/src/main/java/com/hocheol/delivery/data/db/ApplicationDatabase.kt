package com.hocheol.delivery.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hocheol.delivery.data.db.dao.FoodMenuBasketDao
import com.hocheol.delivery.data.db.dao.LocationDao
import com.hocheol.delivery.data.db.dao.RestaurantDao
import com.hocheol.delivery.data.entity.location.LocationLatLngEntity
import com.hocheol.delivery.data.entity.restaurant.RestaurantEntity
import com.hocheol.delivery.data.entity.restaurant.RestaurantFoodEntity

@Database(
    entities = [LocationLatLngEntity::class, RestaurantEntity::class, RestaurantFoodEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun LocationDao(): LocationDao

    abstract fun RestaurantDao(): RestaurantDao

    abstract fun FoodMenuBasketDao(): FoodMenuBasketDao

    companion object {
        const val DB_NAME = "ApplicationDatabase.db"
    }

}