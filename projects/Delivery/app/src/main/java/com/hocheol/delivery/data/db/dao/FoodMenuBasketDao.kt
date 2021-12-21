package com.hocheol.delivery.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hocheol.delivery.data.entity.restaurant.RestaurantFoodEntity

@Dao
interface FoodMenuBasketDao {

    @Query("SELECT * FROM RestaurantFoodEntity WHERE id=:foodId AND restaurantId=:restaurantId")
    fun get(restaurantId: Long, foodId: Long): RestaurantFoodEntity?

    @Query("SELECT * FROM RestaurantFoodEntity")
    fun getAll(): List<RestaurantFoodEntity>

    @Query("SELECT * FROM RestaurantFoodEntity WHERE restaurantId=:restaurantId")
    fun getAllByRestaurantId(restaurantId: Long): List<RestaurantFoodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(FoodEntity: RestaurantFoodEntity)

    @Query("DELETE FROM RestaurantFoodEntity WHERE id=:id")
    fun delete(id: String)

    @Query("DELETE FROM RestaurantFoodEntity")
    fun deleteAll()

}
