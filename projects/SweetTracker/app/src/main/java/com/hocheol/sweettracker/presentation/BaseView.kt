package com.hocheol.sweettracker.presentation

interface BaseView<PresenterT : BasePresenter> {

    val presenter: PresenterT

}