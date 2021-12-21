package com.hocheol.delivery.model.restaurant.food

import com.hocheol.delivery.data.entity.restaurant.RestaurantFoodEntity
import com.hocheol.delivery.model.CellType
import com.hocheol.delivery.model.Model

data class FoodModel(
    override val id: Long,
    override var type: CellType = CellType.FOOD_CELL,
    val title: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val restaurantId: Long,
    val foodId: String,
    val restaurantTitle: String
) : Model(id, type) {

    fun toEntity(basketIndex: Int) = RestaurantFoodEntity(
        "${foodId}_${basketIndex}", title, description, price, imageUrl, restaurantId, restaurantTitle
    )

}
