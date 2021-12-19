package com.hocheol.delivery.screen.main.home.restaurant.detail

import com.hocheol.delivery.data.entity.RestaurantEntity
import com.hocheol.delivery.data.entity.RestaurantFoodEntity

sealed class RestaurantDetailState {

    object UnInitialized : RestaurantDetailState()

    object Loading : RestaurantDetailState()

    data class Success(
        val restaurantEntity: RestaurantEntity,
        val restaurantFoodList: List<RestaurantFoodEntity>? = null,
        val foodMenuListInBasket: List<RestaurantFoodEntity>? = null,
        val isClearNeedInBasketAndAction: Pair<Boolean, () -> Unit> = Pair(false, {}),
        val isLiked: Boolean? = null
    ) : RestaurantDetailState()

}
