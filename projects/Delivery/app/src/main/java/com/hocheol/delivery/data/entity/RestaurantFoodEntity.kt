package com.hocheol.delivery.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hocheol.delivery.model.restaurant.food.FoodModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class RestaurantFoodEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val restaurantId: Long
) : Parcelable {

    fun toModel() = FoodModel(
        id = hashCode().toLong(),
        title = title,
        description = description,
        price = price,
        imageUrl = imageUrl,
        restaurantId = restaurantId,
        foodId = id
    )

}
