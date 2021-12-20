package com.hocheol.delivery.widget.adapter.listener.order

import com.hocheol.delivery.model.restaurant.food.FoodModel
import com.hocheol.delivery.widget.adapter.listener.AdapterListener

interface OrderMenuListListener : AdapterListener {

    fun onRemoveItem(model: FoodModel)

}