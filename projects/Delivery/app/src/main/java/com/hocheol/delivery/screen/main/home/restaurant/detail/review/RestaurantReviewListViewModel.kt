package com.hocheol.delivery.screen.main.home.restaurant.detail.review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hocheol.delivery.R
import com.hocheol.delivery.data.entity.review.ReviewEntity
import com.hocheol.delivery.data.repository.restaurant.review.DefaultRestaurantReviewRepository
import com.hocheol.delivery.data.repository.restaurant.review.RestaurantReviewRepository
import com.hocheol.delivery.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RestaurantReviewListViewModel(
    private val restaurantTitle: String,
    private val restaurantReviewRepository: RestaurantReviewRepository
) : BaseViewModel() {

    val reviewStateLiveData = MutableLiveData<RestaurantReviewState>(RestaurantReviewState.UnInitialized)

    override fun fetchData(): Job = viewModelScope.launch {
        reviewStateLiveData.value = RestaurantReviewState.Loading

        when (val result = restaurantReviewRepository.getReviews(restaurantTitle)) {
            is DefaultRestaurantReviewRepository.Result.Success<*> -> {
                val reviews = result.data as List<ReviewEntity>
                reviewStateLiveData.value = RestaurantReviewState.Success(
                    reviews.map { it.toModel() }
                )
            }
            is DefaultRestaurantReviewRepository.Result.Error -> {
                reviewStateLiveData.value = RestaurantReviewState.Error(
                    R.string.request_error,
                    result.e
                )
            }
        }
    }

}