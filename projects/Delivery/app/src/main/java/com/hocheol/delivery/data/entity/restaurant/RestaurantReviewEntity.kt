package com.hocheol.delivery.data.entity.restaurant

import android.net.Uri
import com.hocheol.delivery.data.entity.Entity
import com.hocheol.delivery.model.restaurant.review.RestaurantReviewModel

data class RestaurantReviewEntity(
    override val id: Long,
    val title: String,
    val description: String,
    val grade: Int,
    val images: List<Uri>? = null
) : Entity {

    fun toModel() = RestaurantReviewModel(
        id = id,
        title = title,
        description = description,
        grade = grade,
        thumbnailImageUri = images?.first()
    )

}
