package com.hocheol.assistedinjection

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.migration.CustomInject
import dagger.hilt.android.migration.CustomInjection
import javax.inject.Inject

@HiltAndroidApp
@CustomInject
class MyApp : Application() {

    @Inject
    lateinit var userService: UserService

    override fun onCreate() {
        Log.d(TAG, "userService1: ${::userService.isInitialized}") // not initialized
        super.onCreate()
        Log.d(TAG, "userService2: ${::userService.isInitialized}") // not initialized
        CustomInjection.inject(this)
        Log.d(TAG, "userService3: ${::userService.isInitialized}") // initialized
    }

    companion object {
        private const val TAG = "MyApp"
    }
}