package com.hocheol.moviereview.domain.usecase

import com.hocheol.moviereview.data.repository.ReviewRepository
import com.hocheol.moviereview.data.repository.UserRepository
import com.hocheol.moviereview.domain.model.Movie
import com.hocheol.moviereview.domain.model.Review
import com.hocheol.moviereview.domain.model.User

class SubmitReviewUseCase(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke(
        movie: Movie,
        content: String,
        score: Float
    ): Review {
        var user = userRepository.getUser()

        if (user == null) {
            userRepository.saveUser(User())
            user = userRepository.getUser()
        }

        return reviewRepository.addReview(
            Review(
                userId = user!!.id,
                movieId = movie.id,
                content = content,
                score = score
            )
        )
    }

}

