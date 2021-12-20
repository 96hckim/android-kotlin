package com.hocheol.delivery.data.repository.order

import com.google.firebase.firestore.FirebaseFirestore
import com.hocheol.delivery.data.entity.RestaurantFoodEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultOrderRepository(
    private val firestore: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher
) : OrderRepository {

    override suspend fun orderMenu(
        userId: String,
        restaurantId: Long,
        foodMenuList: List<RestaurantFoodEntity>
    ): Result = withContext(ioDispatcher) {
        val orderMenuData = hashMapOf(
            "userId" to userId,
            "restaurantId" to restaurantId,
            "orderMenuList" to foodMenuList
        )

        val result: Result = try {
            firestore
                .collection("order")
                .add(orderMenuData)
            Result.Success<Any>()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e)
        }

        return@withContext result
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