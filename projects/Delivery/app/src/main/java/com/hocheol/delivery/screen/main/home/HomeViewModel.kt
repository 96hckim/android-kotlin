package com.hocheol.delivery.screen.main.home

import androidx.lifecycle.MutableLiveData
import com.hocheol.delivery.screen.base.BaseViewModel
import com.hocheol.delivery.screen.main.home.restaurant.HomeState

class HomeViewModel : BaseViewModel() {

    val homeStateLiveData = MutableLiveData<HomeState>(HomeState.UnInitialized)

}