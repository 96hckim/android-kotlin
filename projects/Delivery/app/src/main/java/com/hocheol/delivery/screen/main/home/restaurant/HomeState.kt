package com.hocheol.delivery.screen.main.home.restaurant

sealed class HomeState {

    object UnInitialized : HomeState()

    object Loading : HomeState()

    object Success : HomeState()

}
