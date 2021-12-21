package com.hocheol.delivery.data.repository.restaurant

import com.hocheol.delivery.data.entity.location.LocationLatLngEntity
import com.hocheol.delivery.data.entity.restaurant.RestaurantEntity
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantCategory

interface RestaurantRepository {

    suspend fun getList(
        restaurantCategory: RestaurantCategory,
        locationLatLngEntity: LocationLatLngEntity
    ): List<RestaurantEntity>

}