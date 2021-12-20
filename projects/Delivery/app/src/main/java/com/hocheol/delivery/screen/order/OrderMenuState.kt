package com.hocheol.delivery.screen.order

import androidx.annotation.StringRes
import com.hocheol.delivery.model.restaurant.food.FoodModel

sealed class OrderMenuState {

    object UnInitialized : OrderMenuState()

    object Loading : OrderMenuState()

    data class Success(
        val restaurantFoodModelList: List<FoodModel>? = null
    ) : OrderMenuState()

    object Order : OrderMenuState()

    data class Error(
        @StringRes val messageId: Int,
        val e: Throwable
    ) : OrderMenuState()

}
