package com.hocheol.restaurantmap

import android.app.Application
import android.content.Context

class RestaurantMapApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RestaurantMapApplication.applicationContext = applicationContext
    }

    companion object {
        lateinit var applicationContext: Context
    }
}