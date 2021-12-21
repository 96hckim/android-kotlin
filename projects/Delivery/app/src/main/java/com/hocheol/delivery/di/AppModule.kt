package com.hocheol.delivery.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.hocheol.delivery.data.entity.location.LocationLatLngEntity
import com.hocheol.delivery.data.entity.location.MapSearchInfoEntity
import com.hocheol.delivery.data.entity.restaurant.RestaurantEntity
import com.hocheol.delivery.data.entity.restaurant.RestaurantFoodEntity
import com.hocheol.delivery.data.preference.AppPreferenceManager
import com.hocheol.delivery.data.repository.map.DefaultMapRepository
import com.hocheol.delivery.data.repository.map.MapRepository
import com.hocheol.delivery.data.repository.order.DefaultOrderRepository
import com.hocheol.delivery.data.repository.order.OrderRepository
import com.hocheol.delivery.data.repository.restaurant.DefaultRestaurantRepository
import com.hocheol.delivery.data.repository.restaurant.RestaurantRepository
import com.hocheol.delivery.data.repository.restaurant.food.DefaultRestaurantFoodRepository
import com.hocheol.delivery.data.repository.restaurant.food.RestaurantFoodRepository
import com.hocheol.delivery.data.repository.restaurant.review.DefaultRestaurantReviewRepository
import com.hocheol.delivery.data.repository.restaurant.review.RestaurantReviewRepository
import com.hocheol.delivery.data.repository.user.DefaultUserRepository
import com.hocheol.delivery.data.repository.user.UserRepository
import com.hocheol.delivery.screen.main.home.HomeViewModel
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantCategory
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantListViewModel
import com.hocheol.delivery.screen.main.home.restaurant.detail.RestaurantDetailViewModel
import com.hocheol.delivery.screen.main.home.restaurant.detail.menu.RestaurantMenuListViewModel
import com.hocheol.delivery.screen.main.home.restaurant.detail.review.RestaurantReviewListViewModel
import com.hocheol.delivery.screen.main.like.RestaurantLikeListViewModel
import com.hocheol.delivery.screen.main.my.MyViewModel
import com.hocheol.delivery.screen.mylocation.MyLocationViewModel
import com.hocheol.delivery.screen.order.OrderMenuListViewModel
import com.hocheol.delivery.screen.review.gallery.GalleryPhotoRepository
import com.hocheol.delivery.screen.review.gallery.GalleryViewModel
import com.hocheol.delivery.util.event.MenuChangeEventBus
import com.hocheol.delivery.util.provider.DefaultResourcesProvider
import com.hocheol.delivery.util.provider.ResourcesProvider
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    // Dispatcher
    single { Dispatchers.Main }
    single { Dispatchers.IO }

    // ResourcesProvider
    single<ResourcesProvider> { DefaultResourcesProvider(androidApplication()) }

    // Preference
    single { AppPreferenceManager(androidApplication()) }

    // Event Bus
    single { MenuChangeEventBus() }

    // Firebase
    single { Firebase.firestore }
    single { FirebaseAuth.getInstance() }
    single { FirebaseStorage.getInstance() }

    // DB
    single { provideDB(androidApplication()) }
    single { provideLocationDao(get()) }
    single { provideRestaurantDao(get()) }
    single { provideFoodMenuBasketDao(get()) }

    // Retrofit
    single { provideGsonConvertFactory() }
    single { buildOkHttpClient() }
    // Map
    single(named("map")) { provideMapRetrofit(get(), get()) }
    single { provideMapApiService(get(qualifier = named("map"))) }
    // Food
    single(named("food")) { provideFoodRetrofit(get(), get()) }
    single { provideFoodApiService(get(qualifier = named("food"))) }

    // ViewModels
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { MyViewModel(get(), get(), get()) }
    viewModel { (restaurantCategory: RestaurantCategory, locationLatLng: LocationLatLngEntity) ->
        RestaurantListViewModel(
            restaurantCategory,
            locationLatLng,
            get()
        )
    }
    viewModel { (mapSearchInfoEntity: MapSearchInfoEntity) -> MyLocationViewModel(mapSearchInfoEntity, get(), get()) }
    viewModel { (restaurantEntity: RestaurantEntity) -> RestaurantDetailViewModel(restaurantEntity, get(), get()) }
    viewModel { (restaurantId: Long, restaurantFoodList: List<RestaurantFoodEntity>) ->
        RestaurantMenuListViewModel(
            restaurantId,
            restaurantFoodList,
            get()
        )
    }
    viewModel { (restaurantTitle: String) -> RestaurantReviewListViewModel(restaurantTitle, get()) }
    viewModel { RestaurantLikeListViewModel(get()) }
    viewModel { OrderMenuListViewModel(get(), get(), get()) }
    viewModel { GalleryViewModel(get()) }

    // Repositories
    single<RestaurantRepository> { DefaultRestaurantRepository(get(), get(), get()) }
    single<MapRepository> { DefaultMapRepository(get(), get()) }
    single<UserRepository> { DefaultUserRepository(get(), get(), get()) }
    single<RestaurantFoodRepository> { DefaultRestaurantFoodRepository(get(), get(), get()) }
    single<RestaurantReviewRepository> { DefaultRestaurantReviewRepository(get(), get()) }
    single<OrderRepository> { DefaultOrderRepository(get(), get()) }
    single { GalleryPhotoRepository(androidApplication()) }

}