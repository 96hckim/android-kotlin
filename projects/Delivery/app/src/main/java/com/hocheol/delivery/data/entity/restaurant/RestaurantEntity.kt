package com.hocheol.delivery.data.entity.restaurant

import android.os.Parcelable
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hocheol.delivery.data.entity.Entity
import com.hocheol.delivery.model.restaurant.RestaurantModel
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantCategory
import com.hocheol.delivery.util.convertor.RoomTypeConverters
import kotlinx.parcelize.Parcelize

@Parcelize
@androidx.room.Entity
@TypeConverters(RoomTypeConverters::class)
data class RestaurantEntity(
    override val id: Long,
    val restaurantInfoId: Long,
    val restaurantCategory: RestaurantCategory,
    @PrimaryKey val restaurantTitle: String,
    val restaurantImageUrl: String,
    val grade: Float,
    val reviewCount: Int,
    val deliveryTimeRange: Pair<Int, Int>,
    val deliveryTipRange: Pair<Int, Int>,
    val restaurantTelNumber: String?
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
        deliveryTipRange = deliveryTipRange,
        restaurantTelNumber = restaurantTelNumber
    )

}