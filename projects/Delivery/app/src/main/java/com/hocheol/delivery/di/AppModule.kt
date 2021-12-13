package com.hocheol.delivery.di

import com.hocheol.delivery.data.repository.DefaultRestaurantRepository
import com.hocheol.delivery.data.repository.RestaurantRepository
import com.hocheol.delivery.screen.main.home.HomeViewModel
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantCategory
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantListViewModel
import com.hocheol.delivery.screen.main.my.MyViewModel
import com.hocheol.delivery.util.provider.DefaultResourcesProvider
import com.hocheol.delivery.util.provider.ResourcesProvider
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Dispatcher
    single { Dispatchers.Main }
    single { Dispatchers.IO }

    // ResourcesProvider
    single<ResourcesProvider> { DefaultResourcesProvider(androidApplication()) }

    // Retrofit
    single { provideGsonConvertFactory() }
    single { buildOkHttpClient() }
    single { provideRetrofit(get(), get()) }

    // ViewModels
    viewModel { HomeViewModel() }
    viewModel { MyViewModel() }
    viewModel { (restaurantCategory: RestaurantCategory) -> RestaurantListViewModel(restaurantCategory, get()) }

    // Repositories
    single<RestaurantRepository> { DefaultRestaurantRepository(get(), get()) }

}