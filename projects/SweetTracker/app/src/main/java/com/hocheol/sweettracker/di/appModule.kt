package com.hocheol.sweettracker.di

import android.app.Activity
import com.hocheol.sweettracker.BuildConfig
import com.hocheol.sweettracker.data.api.SweetTrackerApi
import com.hocheol.sweettracker.data.api.Url
import com.hocheol.sweettracker.data.db.AppDatabase
import com.hocheol.sweettracker.data.preference.PreferenceManager
import com.hocheol.sweettracker.data.preference.SharedPreferenceManager
import com.hocheol.sweettracker.data.repository.ShippingCompanyRepository
import com.hocheol.sweettracker.data.repository.ShippingCompanyRepositoryImpl
import com.hocheol.sweettracker.data.repository.TrackingItemRepository
import com.hocheol.sweettracker.data.repository.TrackingItemRepositoryImpl
import com.hocheol.sweettracker.presentation.addtrackingitem.AddTrackingItemFragment
import com.hocheol.sweettracker.presentation.addtrackingitem.AddTrackingItemPresenter
import com.hocheol.sweettracker.presentation.addtrackingitem.AddTrackingItemsContract
import com.hocheol.sweettracker.presentation.trackingitems.TrackingItemsContract
import com.hocheol.sweettracker.presentation.trackingitems.TrackingItemsFragment
import com.hocheol.sweettracker.presentation.trackingitems.TrackingItemsPresenter
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appModule = module {

    single { Dispatchers.IO }

    // Database
    single { AppDatabase.build(androidApplication()) }
    single { get<AppDatabase>().trackingItemDao() }
    single { get<AppDatabase>().shippingCompanyDao() }

    // Api
    single {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
    }
    single<SweetTrackerApi> {
        Retrofit.Builder()
            .baseUrl(Url.SWEET_TRACKER_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create()
    }

    // Preference
    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE) }
    single<PreferenceManager> { SharedPreferenceManager(get()) }

    // Repository
//    single<TrackingItemRepository> { TrackingItemRepositoryStub() }
    single<TrackingItemRepository> { TrackingItemRepositoryImpl(get(), get(), get()) }
    single<ShippingCompanyRepository> { ShippingCompanyRepositoryImpl(get(), get(), get(), get()) }

    // Presentation
    scope<TrackingItemsFragment> {
        scoped<TrackingItemsContract.Presenter> { TrackingItemsPresenter(getSource(), get()) }
    }
    scope<AddTrackingItemFragment> {
        scoped<AddTrackingItemsContract.Presenter> {
            AddTrackingItemPresenter(getSource(), get(), get())
        }
    }

}