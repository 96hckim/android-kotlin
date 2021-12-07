package com.hocheol.shopping.di

import com.hocheol.shopping.data.network.buildOkHttpClient
import com.hocheol.shopping.data.network.provideGsonConverterFactory
import com.hocheol.shopping.data.network.provideProductApiService
import com.hocheol.shopping.data.network.provideProductRetrofit
import com.hocheol.shopping.data.repository.DefaultProductRepository
import com.hocheol.shopping.data.repository.ProductRepository
import com.hocheol.shopping.domain.product.GetProductItemUseCase
import kotlinx.coroutines.Dispatchers
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
    single<ProductRepository> { DefaultProductRepository(get(), get()) }

    // UseCases
    factory { GetProductItemUseCase(get()) }

}