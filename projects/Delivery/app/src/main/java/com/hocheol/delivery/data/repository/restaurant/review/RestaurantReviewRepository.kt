package com.hocheol.delivery.data.repository.restaurant.review

import com.hocheol.delivery.data.entity.RestaurantReviewEntity

interface RestaurantReviewRepository {

    suspend fun getReviews(restaurantTitle: String): List<RestaurantReviewEntity>

}