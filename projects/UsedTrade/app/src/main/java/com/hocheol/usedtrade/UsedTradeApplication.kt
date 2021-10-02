package com.hocheol.usedtrade

import android.app.Application
import android.content.Context
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig

class UsedTradeApplication : Application(), CameraXConfig.Provider {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
    }

    override fun onTerminate() {
        super.onTerminate()

        appContext = null
    }

    override fun getCameraXConfig(): CameraXConfig = Camera2Config.defaultConfig()

    companion object {
        var appContext: Context? = null
            private set
    }

}