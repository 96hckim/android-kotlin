package com.hocheol.delivery.screen.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hocheol.delivery.data.repository.restaurant.food.RestaurantFoodRepository
import com.hocheol.delivery.model.CellType
import com.hocheol.delivery.model.restaurant.food.FoodModel
import com.hocheol.delivery.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OrderMenuListViewModel(
    private val restaurantFoodRepository: RestaurantFoodRepository
) : BaseViewModel() {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    val orderMenuStateLiveData = MutableLiveData<OrderMenuState>(OrderMenuState.UnInitialized)

    override fun fetchData(): Job = viewModelScope.launch {
        orderMenuStateLiveData.value = OrderMenuState.Loading
        val foodMenuList = restaurantFoodRepository.getAllFoodMenuListInBasket()
        orderMenuStateLiveData.value = OrderMenuState.Success(
            foodMenuList.map {
                it.toModel().apply {
                    type = CellType.ORDER_FOOD_CELL
                }
            }
        )
    }

    fun orderMenu() {
    }

    fun clearOrderMenu() {
    }

    fun removeOrderMenu(model: FoodModel) {
    }

}