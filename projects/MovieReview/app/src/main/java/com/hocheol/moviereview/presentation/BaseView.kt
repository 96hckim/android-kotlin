package com.hocheol.moviereview.presentation

interface BaseView<PresenterT : BasePresenter> {

    val presenter: PresenterT

}