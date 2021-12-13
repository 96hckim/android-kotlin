package com.hocheol.delivery.di

import com.hocheol.delivery.util.provider.DefaultResourcesProvider
import com.hocheol.delivery.util.provider.ResourcesProvider
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    // Dispatcher
    single { Dispatchers.Main }
    single { Dispatchers.IO }

    // ResourcesProvider
    single<ResourcesProvider> { DefaultResourcesProvider(androidContext()) }

    // Retrofit
    single { provideGsonConvertFactory() }
    single { buildOkHttpClient() }
    single { provideRetrofit(get(), get()) }

}