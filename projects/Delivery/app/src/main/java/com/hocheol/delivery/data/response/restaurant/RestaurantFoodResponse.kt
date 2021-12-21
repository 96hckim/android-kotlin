package com.hocheol.delivery.data.response.restaurant

import com.hocheol.delivery.data.entity.restaurant.RestaurantFoodEntity

data class RestaurantFoodResponse(
    val id: String,
    val title: String,
    val description: String,
    val price: String,
    val imageUrl: String
) {

    fun toEntity(restaurantId: Long, restaurantTitle: String) = RestaurantFoodEntity(
        id,
        title,
        description,
        price.toDouble().toInt(),
        imageUrl,
        restaurantId,
        restaurantTitle
    )

}
