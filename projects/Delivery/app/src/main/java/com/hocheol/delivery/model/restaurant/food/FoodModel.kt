package com.hocheol.delivery.model.restaurant.food

import com.hocheol.delivery.model.CellType
import com.hocheol.delivery.model.Model

data class FoodModel(
    override val id: Long,
    override val type: CellType = CellType.FOOD_CELL,
    val title: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val restaurantId: Long
) : Model(id, type)
