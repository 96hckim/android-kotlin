package com.hocheol.delivery.widget.adapter.listener.restaurant

import com.hocheol.delivery.model.restaurant.RestaurantModel
import com.hocheol.delivery.widget.adapter.listener.AdapterListener

interface RestaurantListListener : AdapterListener {

    fun onClickItem(model: RestaurantModel)

}