package com.hocheol.delivery.screen.main.home.restaurant

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hocheol.delivery.data.entity.location.LocationLatLngEntity
import com.hocheol.delivery.data.repository.restaurant.RestaurantRepository
import com.hocheol.delivery.model.restaurant.RestaurantModel
import com.hocheol.delivery.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RestaurantListViewModel(
    private val restaurantCategory: RestaurantCategory,
    private var locationLatLng: LocationLatLngEntity,
    private val restaurantRepository: RestaurantRepository,
    private var restaurantOrder: RestaurantOrder = RestaurantOrder.DEFAULT
) : BaseViewModel() {

    val restaurantListLiveData = MutableLiveData<List<RestaurantModel>>()

    override fun fetchData(): Job = viewModelScope.launch {
        val restaurantList = restaurantRepository.getList(restaurantCategory, locationLatLng)

        val sortedList = when (restaurantOrder) {
            RestaurantOrder.DEFAULT -> {
                restaurantList
            }
            RestaurantOrder.LOW_DELIVERY_TIP -> {
                restaurantList.sortedBy { it.deliveryTipRange.first }
            }
            RestaurantOrder.FAST_DELIVERY -> {
                restaurantList.sortedBy { it.deliveryTimeRange.first }
            }
            RestaurantOrder.TOP_RATE -> {
                restaurantList.sortedByDescending { it.grade }
            }
        }

        restaurantListLiveData.value = sortedList.map {
            it.toModel()
        }
    }

    fun setLocationLatLng(locationLatLng: LocationLatLngEntity) {
        this.locationLatLng = locationLatLng
        fetchData()
    }

    fun setRestaurantOrder(order: RestaurantOrder) {
        this.restaurantOrder = order
        fetchData()
    }

}