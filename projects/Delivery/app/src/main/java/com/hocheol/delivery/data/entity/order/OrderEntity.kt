package com.hocheol.delivery.data.entity.order

import com.hocheol.delivery.data.entity.restaurant.RestaurantFoodEntity
import com.hocheol.delivery.model.restaurant.order.OrderModel

data class OrderEntity(
    val id: String,
    val userId: String,
    val restaurantId: Long,
    val foodMenuList: List<RestaurantFoodEntity>,
    val restaurantTitle: String
) {

    fun toModel() = OrderModel(
        id = hashCode().toLong(),
        orderId = id,
        userId = userId,
        restaurantId = restaurantId,
        foodMenuList = foodMenuList,
        restaurantTitle = restaurantTitle
    )

}
