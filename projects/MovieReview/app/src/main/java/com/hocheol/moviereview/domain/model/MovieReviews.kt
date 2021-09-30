package com.hocheol.moviereview.domain.model

data class MovieReviews(
    val myReview: Review?,
    val othersReview: List<Review>
)
