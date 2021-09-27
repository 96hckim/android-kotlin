package com.hocheol.subway.di

import android.app.Activity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.hocheol.subway.data.api.StationApi
import com.hocheol.subway.data.api.StationStorageApi
import com.hocheol.subway.data.preference.PreferenceManager
import com.hocheol.subway.data.preference.SharedPreferenceManager
import com.hocheol.subway.data.repository.StationRepository
import com.hocheol.subway.data.repository.StationRepositoryImpl
import fastcampus.aop.part5.chapter05.data.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single { Dispatchers.IO }

    // Database
    single { AppDatabase.build(androidApplication()) }
    single { get<AppDatabase>().stationDao() }

    // Preference
    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE) }
    single<PreferenceManager> { SharedPreferenceManager(get()) }

    // Api
    single<StationApi> { StationStorageApi(Firebase.storage) }

    // Repository
    single<StationRepository> { StationRepositoryImpl(get(), get(), get(), get()) }

}