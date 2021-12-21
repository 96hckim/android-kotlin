package com.hocheol.delivery.widget.adapter.listener.order

import com.hocheol.delivery.widget.adapter.listener.AdapterListener

interface OrderListListener : AdapterListener {

    fun writeRestaurantReview(orderId: String, restaurantTitle: String)

}