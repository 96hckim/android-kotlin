package com.hocheol.delivery.data.entity

import android.os.Parcelable
import com.hocheol.delivery.model.restaurant.RestaurantModel
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantCategory
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestaurantEntity(
    override val id: Long,
    val restaurantInfoId: Long,
    val restaurantCategory: RestaurantCategory,
    val restaurantTitle: String,
    val restaurantImageUrl: String,
    val grade: Float,
    val reviewCount: Int,
    val deliveryTimeRange: Pair<Int, Int>,
    val deliveryTipRange: Pair<Int, Int>
) : Entity, Parcelable {

    fun toModel() = RestaurantModel(
        id = id,
        restaurantInfoId = restaurantInfoId,
        restaurantCategory = restaurantCategory,
        restaurantTitle = restaurantTitle,
        restaurantImageUrl = restaurantImageUrl,
        grade = grade,
        reviewCount = reviewCount,
        deliveryTimeRange = deliveryTimeRange,
        deliveryTipRange = deliveryTipRange
    )

}