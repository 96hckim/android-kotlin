package com.hocheol.moviereview.presentation.home

import com.hocheol.moviereview.domain.model.FeaturedMovie
import com.hocheol.moviereview.domain.model.Movie
import com.hocheol.moviereview.presentation.BasePresenter
import com.hocheol.moviereview.presentation.BaseView

interface HomeContract {

    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showErrorDescription(message: String)

        fun showMovies(
            featuredMovie: FeaturedMovie?,
            movies: List<Movie>
        )

    }

    interface Presenter : BasePresenter

}
