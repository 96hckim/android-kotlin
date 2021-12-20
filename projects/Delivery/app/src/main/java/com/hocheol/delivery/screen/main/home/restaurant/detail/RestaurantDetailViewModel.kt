package com.hocheol.delivery.screen.main.home.restaurant.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hocheol.delivery.data.entity.RestaurantEntity
import com.hocheol.delivery.data.entity.RestaurantFoodEntity
import com.hocheol.delivery.data.repository.restaurant.food.RestaurantFoodRepository
import com.hocheol.delivery.data.repository.user.UserRepository
import com.hocheol.delivery.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RestaurantDetailViewModel(
    private val restaurantEntity: RestaurantEntity,
    private val restaurantFoodRepository: RestaurantFoodRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val restaurantDetailStateLiveData = MutableLiveData<RestaurantDetailState>(RestaurantDetailState.UnInitialized)

    override fun fetchData(): Job = viewModelScope.launch {
        restaurantDetailStateLiveData.value = RestaurantDetailState.Loading
        val foods = restaurantFoodRepository.getFoods(restaurantId = restaurantEntity.restaurantInfoId)
        val foodMenuListInBasket = restaurantFoodRepository.getAllFoodMenuListInBasket()
        val isLiked = userRepository.getUserLikeRestaurant(restaurantEntity.restaurantTitle) != null
        restaurantDetailStateLiveData.value = RestaurantDetailState.Success(
            restaurantEntity = restaurantEntity,
            restaurantFoodList = foods,
            foodMenuListInBasket = foodMenuListInBasket,
            isLiked = isLiked
        )
    }

    fun getRestaurantTelNumber(): String? {
        return when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                data.restaurantEntity.restaurantTelNumber
            }
            else -> null
        }
    }

    fun getRestaurantInfo(): RestaurantEntity? {
        return when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                data.restaurantEntity
            }
            else -> null
        }
    }

    fun toggleLikeRestaurant() = viewModelScope.launch {
        when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                userRepository.getUserLikeRestaurant(restaurantEntity.restaurantTitle)?.let {
                    userRepository.deleteUserLikedRestaurant(it.restaurantTitle)
                    restaurantDetailStateLiveData.value = data.copy(
                        isLiked = false
                    )
                } ?: kotlin.run {
                    userRepository.insertUserLikedRestaurant(restaurantEntity)
                    restaurantDetailStateLiveData.value = data.copy(
                        isLiked = true
                    )
                }
            }
            else -> {}
        }
    }

    fun notifyFoodMenuListInBasket(foodMenu: RestaurantFoodEntity) = viewModelScope.launch {
        when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                restaurantDetailStateLiveData.value = data.copy(
                    foodMenuListInBasket = data.foodMenuListInBasket?.toMutableList()?.apply {
                        add(foodMenu)
                    }
                )
            }
            else -> Unit
        }
    }

    fun notifyFoodMenuListInBasket(isClearNeedInBasketAndAction: Pair<Boolean, () -> Unit>) {
        when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                restaurantDetailStateLiveData.value = data.copy(
                    isClearNeedInBasketAndAction = isClearNeedInBasketAndAction
                )
            }
            else -> Unit
        }
    }

    fun notifyClearBasket() = viewModelScope.launch {
        when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                restaurantDetailStateLiveData.value = data.copy(
                    foodMenuListInBasket = listOf(),
                    isClearNeedInBasketAndAction = Pair(false, {})
                )
            }
            else -> Unit
        }
    }

    fun checkMyBasket() = viewModelScope.launch {
        when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                restaurantDetailStateLiveData.value = data.copy(
                    foodMenuListInBasket = restaurantFoodRepository.getAllFoodMenuListInBasket()
                )
            }
            else -> Unit
        }
    }

}