package com.hocheol.delivery.screen.main.home.restaurant.detail

import com.hocheol.delivery.data.entity.RestaurantEntity

sealed class RestaurantDetailState {

    object UnInitialized : RestaurantDetailState()

    object Loading : RestaurantDetailState()

    data class Success(
        val restaurantEntity: RestaurantEntity
    ) : RestaurantDetailState()

}
