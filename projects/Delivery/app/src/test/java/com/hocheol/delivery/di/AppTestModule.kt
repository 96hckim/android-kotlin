package com.hocheol.delivery.di

import com.google.firebase.auth.FirebaseAuth
import com.hocheol.delivery.data.TestOrderRepository
import com.hocheol.delivery.data.TestRestaurantFoodRepository
import com.hocheol.delivery.data.TestRestaurantRepository
import com.hocheol.delivery.data.entity.location.LocationLatLngEntity
import com.hocheol.delivery.data.repository.order.OrderRepository
import com.hocheol.delivery.data.repository.restaurant.RestaurantRepository
import com.hocheol.delivery.data.repository.restaurant.food.RestaurantFoodRepository
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantCategory
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantListViewModel
import com.hocheol.delivery.screen.order.OrderMenuListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    // ViewModels
    viewModel { (restaurantCategory: RestaurantCategory, locationLatLng: LocationLatLngEntity) ->
        RestaurantListViewModel(
            restaurantCategory,
            locationLatLng,
            get()
        )
    }
    viewModel { (firebaseAuth: FirebaseAuth) -> OrderMenuListViewModel(get(), get(), firebaseAuth) }

    // Repositories
    single<RestaurantRepository> { TestRestaurantRepository() }
    single<RestaurantFoodRepository> { TestRestaurantFoodRepository() }
    single<OrderRepository> { TestOrderRepository() }

}