package com.hocheol.delivery.screen.main.home.restaurant

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hocheol.delivery.data.entity.LocationLatLngEntity
import com.hocheol.delivery.data.repository.restaurant.RestaurantRepository
import com.hocheol.delivery.model.restaurant.RestaurantModel
import com.hocheol.delivery.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RestaurantListViewModel(
    private val restaurantCategory: RestaurantCategory,
    private var locationLatLng: LocationLatLngEntity,
    private val restaurantRepository: RestaurantRepository
) : BaseViewModel() {

    val restaurantListLiveData = MutableLiveData<List<RestaurantModel>>()

    override fun fetchData(): Job = viewModelScope.launch {
        val restaurantList = restaurantRepository.getList(restaurantCategory, locationLatLng)
        restaurantListLiveData.value = restaurantList.map {
            it.toModel()
        }
    }

    fun setLocationLatLng(locationLatLng: LocationLatLngEntity) {
        this.locationLatLng = locationLatLng
        fetchData()
    }

}