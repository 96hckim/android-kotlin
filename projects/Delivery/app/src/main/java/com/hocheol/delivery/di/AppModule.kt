package com.hocheol.delivery.di

import com.hocheol.delivery.data.entity.LocationLatLngEntity
import com.hocheol.delivery.data.entity.MapSearchInfoEntity
import com.hocheol.delivery.data.entity.RestaurantEntity
import com.hocheol.delivery.data.repository.map.DefaultMapRepository
import com.hocheol.delivery.data.repository.map.MapRepository
import com.hocheol.delivery.data.repository.restaurant.DefaultRestaurantRepository
import com.hocheol.delivery.data.repository.restaurant.RestaurantRepository
import com.hocheol.delivery.data.repository.user.DefaultUserRepository
import com.hocheol.delivery.data.repository.user.UserRepository
import com.hocheol.delivery.screen.main.home.HomeViewModel
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantCategory
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantListViewModel
import com.hocheol.delivery.screen.main.home.restaurant.detail.RestaurantDetailViewModel
import com.hocheol.delivery.screen.main.my.MyViewModel
import com.hocheol.delivery.screen.mylocation.MyLocationViewModel
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

    // DB
    single { provideDB(androidApplication()) }
    single { provideLocationDao(get()) }

    // Retrofit
    single { provideGsonConvertFactory() }
    single { buildOkHttpClient() }
    // Map
    single { provideMapRetrofit(get(), get()) }
    single { provideMapApiService(get()) }

    // ViewModels
    viewModel { HomeViewModel(get(), get()) }
    viewModel { MyViewModel() }
    viewModel { (restaurantCategory: RestaurantCategory, locationLatLng: LocationLatLngEntity) ->
        RestaurantListViewModel(
            restaurantCategory,
            locationLatLng,
            get()
        )
    }
    viewModel { (mapSearchInfoEntity: MapSearchInfoEntity) -> MyLocationViewModel(mapSearchInfoEntity, get(), get()) }
    viewModel { (restaurantEntity: RestaurantEntity) -> RestaurantDetailViewModel(restaurantEntity) }

    // Repositories
    single<RestaurantRepository> { DefaultRestaurantRepository(get(), get(), get()) }
    single<MapRepository> { DefaultMapRepository(get(), get()) }
    single<UserRepository> { DefaultUserRepository(get(), get()) }

}