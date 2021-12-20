package com.hocheol.delivery.screen.main.like

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hocheol.delivery.data.entity.RestaurantEntity
import com.hocheol.delivery.data.repository.user.UserRepository
import com.hocheol.delivery.model.CellType
import com.hocheol.delivery.model.restaurant.RestaurantModel
import com.hocheol.delivery.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RestaurantLikeListViewModel(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val restaurantListLiveData = MutableLiveData<List<RestaurantModel>>()

    override fun fetchData(): Job = viewModelScope.launch {
        restaurantListLiveData.value = userRepository.getAllUserLikedRestaurant().map {
            it.toModel().apply {
                type = CellType.LIKE_RESTAURANT_CELL
            }
        }
    }

    fun dislikeRestaurant(restaurant: RestaurantEntity) = viewModelScope.launch {
        userRepository.deleteUserLikedRestaurant(restaurant.restaurantTitle)
        fetchData()
    }

}