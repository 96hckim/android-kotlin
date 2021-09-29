package com.hocheol.moviereview.domain.usecase

import com.hocheol.moviereview.data.repository.ReviewRepository
import com.hocheol.moviereview.domain.model.Review

class GetAllReviewsUseCase(private val reviewRepository: ReviewRepository) {

    suspend operator fun invoke(movieId: String):List<Review> =
        reviewRepository.getAllReviews(movieId)

}