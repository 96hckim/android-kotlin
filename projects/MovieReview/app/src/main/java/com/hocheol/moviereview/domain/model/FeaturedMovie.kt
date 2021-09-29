package com.hocheol.moviereview.domain.model

data class FeaturedMovie(
    val movie: Movie,
    val latestReview: Review?
)
