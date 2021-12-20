package com.hocheol.delivery.data.repository.restaurant.review

import com.hocheol.delivery.data.entity.RestaurantReviewEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultRestaurantReviewRepository(
    private val ioDispatcher: CoroutineDispatcher
) : RestaurantReviewRepository {

    override suspend fun getReviews(restaurantTitle: String): List<RestaurantReviewEntity> = withContext(ioDispatcher) {
        return@withContext (0..10).map {
            RestaurantReviewEntity(
                id = 0,
                title = "제목 $it",
                description = "내용 $it",
                grade = (0..5).random()
            )
        }
    }

}