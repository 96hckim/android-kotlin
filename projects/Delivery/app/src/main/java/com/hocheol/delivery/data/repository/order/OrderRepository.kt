package com.hocheol.delivery.data.repository.order

import com.hocheol.delivery.data.entity.RestaurantFoodEntity

interface OrderRepository {

    suspend fun orderMenu(
        userId: String,
        restaurantId: Long,
        foodMenuList: List<RestaurantFoodEntity>
    ): DefaultOrderRepository.Result

    suspend fun getAllOrderMenus(
        userId: String
    ): DefaultOrderRepository.Result

}