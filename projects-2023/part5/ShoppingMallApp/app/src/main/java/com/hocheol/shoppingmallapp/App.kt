package com.hocheol.shoppingmallapp

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "39dba980b4f77dfca3fca9e58ad98553")
        MobileAds.initialize(this) {}
    }
}