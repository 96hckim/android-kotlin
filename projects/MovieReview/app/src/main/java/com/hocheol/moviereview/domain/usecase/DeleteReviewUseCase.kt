package com.hocheol.moviereview.domain.usecase

import com.hocheol.moviereview.data.repository.ReviewRepository
import com.hocheol.moviereview.domain.model.Review

class DeleteReviewUseCase(
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke(review: Review) =
        reviewRepository.removeReview(review)

}
