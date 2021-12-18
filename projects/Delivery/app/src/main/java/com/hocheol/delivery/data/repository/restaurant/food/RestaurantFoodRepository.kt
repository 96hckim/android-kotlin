package com.hocheol.delivery.data.repository.restaurant.food

import com.hocheol.delivery.data.entity.RestaurantFoodEntity

interface RestaurantFoodRepository {

    suspend fun getFoods(restaurantId: Long): List<RestaurantFoodEntity>

}