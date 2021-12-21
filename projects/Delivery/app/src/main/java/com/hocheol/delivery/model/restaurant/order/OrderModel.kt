package com.hocheol.delivery.model.restaurant.order

import com.hocheol.delivery.data.entity.OrderEntity
import com.hocheol.delivery.data.entity.RestaurantFoodEntity
import com.hocheol.delivery.model.CellType
import com.hocheol.delivery.model.Model

data class OrderModel(
    override val id: Long,
    override val type: CellType = CellType.ORDER_CELL,
    val orderId: String,
    val userId: String,
    val restaurantId: Long,
    val foodMenuList: List<RestaurantFoodEntity>
) : Model(id, type) {

    fun toEntity() = OrderEntity(
        id = orderId,
        userId = userId,
        restaurantId = restaurantId,
        foodMenuList = foodMenuList
    )

}
