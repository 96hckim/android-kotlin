package com.hocheol.delivery.widget.adapter.listener.restaurant

import com.hocheol.delivery.model.restaurant.food.FoodModel
import com.hocheol.delivery.widget.adapter.listener.AdapterListener

interface FoodMenuListListener : AdapterListener {

    fun onClickItem(model: FoodModel)

}
