package com.hocheol.delivery.data.repository.restaurant.review

import com.hocheol.delivery.data.entity.restaurant.RestaurantReviewEntity

interface RestaurantReviewRepository {

    suspend fun getReviews(restaurantTitle: String): List<RestaurantReviewEntity>

}