package com.hocheol.shopping.di

import com.hocheol.shopping.data.db.provideDB
import com.hocheol.shopping.data.db.provideProductDao
import com.hocheol.shopping.data.network.buildOkHttpClient
import com.hocheol.shopping.data.network.provideGsonConverterFactory
import com.hocheol.shopping.data.network.provideProductApiService
import com.hocheol.shopping.data.network.provideProductRetrofit
import com.hocheol.shopping.data.preference.PreferenceManager
import com.hocheol.shopping.data.repository.DefaultProductRepository
import com.hocheol.shopping.data.repository.ProductRepository
import com.hocheol.shopping.domain.product.GetProductItemUseCase
import com.hocheol.shopping.domain.product.GetProductListUseCase
import com.hocheol.shopping.domain.product.OrderProductItemUseCase
import com.hocheol.shopping.presentation.detail.ProductDetailViewModel
import com.hocheol.shopping.presentation.list.ProductListViewModel
import com.hocheol.shopping.presentation.main.MainViewModel
import com.hocheol.shopping.presentation.profile.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Coroutines Dispatcher
    single { Dispatchers.Main }
    single { Dispatchers.IO }

    // Network
    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }
    single { provideProductRetrofit(get(), get()) }
    single { provideProductApiService(get()) }

    // Repositories
    single<ProductRepository> { DefaultProductRepository(get(), get(), get()) }

    // UseCases
    factory { GetProductItemUseCase(get()) }
    factory { GetProductListUseCase(get()) }
    factory { OrderProductItemUseCase(get()) }

    // ViewModels
    viewModel { MainViewModel() }
    viewModel { ProductListViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { (productId: Long) -> ProductDetailViewModel(productId, get(), get()) }

    // Database
    single { provideDB(androidApplication()) }
    single { provideProductDao(get()) }

    // PreferenceManager
    single { PreferenceManager(androidContext()) }

}