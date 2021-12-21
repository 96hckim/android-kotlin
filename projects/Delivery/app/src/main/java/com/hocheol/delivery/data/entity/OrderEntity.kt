package com.hocheol.delivery.data.entity

import com.hocheol.delivery.model.restaurant.order.OrderModel

data class OrderEntity(
    val id: String,
    val userId: String,
    val restaurantId: Long,
    val foodMenuList: List<RestaurantFoodEntity>
) {

    fun toModel() = OrderModel(
        id = hashCode().toLong(),
        orderId = id,
        userId = userId,
        restaurantId = restaurantId,
        foodMenuList = foodMenuList
    )

}
