package com.hocheol.moviereview.presentation.reviews

import com.hocheol.moviereview.domain.model.Movie
import com.hocheol.moviereview.domain.model.MovieReviews
import com.hocheol.moviereview.domain.model.Review
import com.hocheol.moviereview.presentation.BasePresenter
import com.hocheol.moviereview.presentation.BaseView

interface MovieReviewsContract {

    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showErrorDescription(message: String)

        fun showMovieInformation(movie: Movie)

        fun showReviews(reviews: MovieReviews)

        fun showErrorToast(message: String)

    }

    interface Presenter : BasePresenter {

        val movie: Movie

        fun requestAddReview(content: String, score: Float)

        fun requestRemoveReview(review: Review)

    }

}
