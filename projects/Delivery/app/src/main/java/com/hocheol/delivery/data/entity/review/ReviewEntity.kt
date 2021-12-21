package com.hocheol.delivery.data.entity.review

import android.net.Uri
import com.hocheol.delivery.model.restaurant.review.ReviewModel

data class ReviewEntity(
    val userId: String,
    val title: String,
    val createdAt: Long,
    val content: String,
    val rating: Float,
    val imageUrlList: List<String>? = null,
    val orderId: String,
    val restaurantTitle: String
) {

    fun toModel() = ReviewModel(
        id = hashCode().toLong(),
        title = title,
        description = content,
        grade = rating,
        thumbnailImageUri = if (imageUrlList.isNullOrEmpty()) {
            null
        } else {
            Uri.parse(imageUrlList.first())
        }
    )

}
