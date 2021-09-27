package com.hocheol.subway.presenter

interface BaseView<PresenterT : BasePresenter> {

    val presenter: PresenterT

}