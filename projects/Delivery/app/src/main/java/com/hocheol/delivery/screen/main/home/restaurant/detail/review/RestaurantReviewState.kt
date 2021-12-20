package com.hocheol.delivery.screen.main.home.restaurant.detail.review

import com.hocheol.delivery.model.restaurant.review.RestaurantReviewModel

sealed class RestaurantReviewState {

    object UnInitialized : RestaurantReviewState()

    object Loading : RestaurantReviewState()

    data class Success(
        val reviewList: List<RestaurantReviewModel>
    ) : RestaurantReviewState()

}
