package com.hocheol.delivery.data.repository.restaurant

import com.hocheol.delivery.data.entity.RestaurantEntity
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantCategory

interface RestaurantRepository {

    suspend fun getList(
        restaurantCategory: RestaurantCategory
    ): List<RestaurantEntity>

}