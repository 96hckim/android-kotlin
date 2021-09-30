package com.hocheol.moviereview.domain.usecase

import com.hocheol.moviereview.data.repository.ReviewRepository
import com.hocheol.moviereview.data.repository.UserRepository

class GetAllMovieReviewsUseCase(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) {

//    suspend operator fun invoke(movieId: String): MovieReviews {
//        val reviews = reviewRepository.getAllMovieReviews(movieId)
//        val user = userRepository.getUser()
//
//        if (user == null) {
//            userRepository.saveUser(User())
//
//            return MovieReviews(null, reviews)
//        }
//
//        return MovieReviews(
//            reviews.find { it.userId == user.id },
//            reviews.filter { it.userId != user.id }
//        )
//    }

}
