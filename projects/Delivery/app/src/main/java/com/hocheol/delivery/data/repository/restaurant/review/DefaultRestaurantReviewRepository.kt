package com.hocheol.delivery.data.repository.restaurant.review

import com.google.firebase.firestore.FirebaseFirestore
import com.hocheol.delivery.data.entity.review.ReviewEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DefaultRestaurantReviewRepository(
    private val firestore: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher
) : RestaurantReviewRepository {

    override suspend fun getReviews(restaurantTitle: String): Result = withContext(ioDispatcher) {
        // test
//        return@withContext (0..10).map {
//            RestaurantReviewEntity(
//                id = 0,
//                title = "제목 $it",
//                description = "내용 $it",
//                grade = (1..5).random()
//            )
//        }

        return@withContext try {
            val snapshot = firestore
                .collection("review")
                .whereEqualTo("restaurantTitle", restaurantTitle)
                .get()
                .await()

            Result.Success(snapshot.documents.map {
                ReviewEntity(
                    userId = it.get("userId") as String,
                    title = it.get("title") as String,
                    createdAt = it.get("createdAt") as Long,
                    content = it.get("content") as String,
                    rating = (it.get("rating") as Double).toFloat(),
                    imageUrlList = it.get("imageUrlList") as? List<String>,
                    orderId = it.get("orderId") as String,
                    restaurantTitle = it.get("restaurantTitle") as String
                )
            })
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e)
        }
    }

    sealed class Result {

        data class Success<T>(
            val data: T? = null
        ) : Result()

        data class Error(
            val e: Throwable
        ) : Result()

    }

}