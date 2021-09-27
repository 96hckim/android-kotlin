package com.hocheol.subway.presentation

interface BaseView<PresenterT : BasePresenter> {

    val presenter: PresenterT

}