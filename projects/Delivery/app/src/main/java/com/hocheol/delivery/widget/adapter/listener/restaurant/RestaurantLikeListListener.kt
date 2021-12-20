package com.hocheol.delivery.widget.adapter.listener.restaurant

import com.hocheol.delivery.model.restaurant.RestaurantModel

interface RestaurantLikeListListener : RestaurantListListener {

    fun onDislikeItem(model: RestaurantModel)

}