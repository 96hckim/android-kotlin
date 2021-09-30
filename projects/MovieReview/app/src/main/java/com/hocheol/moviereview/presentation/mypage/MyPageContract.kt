package com.hocheol.moviereview.presentation.mypage

import com.hocheol.moviereview.domain.model.ReviewedMovie
import com.hocheol.moviereview.presentation.BasePresenter
import com.hocheol.moviereview.presentation.BaseView

interface MyPageContract {

    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showNoDataDescription(message: String)

        fun showErrorDescription(message: String)

        fun showReviewedMovies(reviewedMovies: List<ReviewedMovie>)

    }

    interface Presenter : BasePresenter

}
