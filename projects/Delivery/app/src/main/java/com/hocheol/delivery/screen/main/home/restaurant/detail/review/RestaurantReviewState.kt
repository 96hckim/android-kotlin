package com.hocheol.delivery.screen.main.home.restaurant.detail.review

import androidx.annotation.StringRes
import com.hocheol.delivery.model.restaurant.review.ReviewModel

sealed class RestaurantReviewState {

    object UnInitialized : RestaurantReviewState()

    object Loading : RestaurantReviewState()

    data class Success(
        val reviewList: List<ReviewModel>
    ) : RestaurantReviewState()

    data class Error(
        @StringRes val messageId: Int,
        val e: Throwable
    ) : RestaurantReviewState()

}
