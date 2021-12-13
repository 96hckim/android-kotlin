package com.hocheol.delivery.screen.main.home.restaurant

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hocheol.delivery.data.entity.RestaurantEntity
import com.hocheol.delivery.data.repository.RestaurantRepository
import com.hocheol.delivery.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RestaurantListViewModel(
    private val restaurantCategory: RestaurantCategory,
    private val restaurantRepository: RestaurantRepository
) : BaseViewModel() {

    val restaurantListLiveData = MutableLiveData<List<RestaurantEntity>>()

    override fun fetchData(): Job = viewModelScope.launch {
        val restaurantList = restaurantRepository.getList(restaurantCategory)
        restaurantListLiveData.value = restaurantList
    }

}