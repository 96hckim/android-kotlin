package com.hocheol.delivery.screen.mylocation

import androidx.annotation.StringRes
import com.hocheol.delivery.data.entity.location.MapSearchInfoEntity

sealed class MyLocationState {

    object UnInitialized : MyLocationState()

    object Loading : MyLocationState()

    data class Success(
        val mapSearchInfo: MapSearchInfoEntity
    ) : MyLocationState()

    data class Confirm(
        val mapSearchInfo: MapSearchInfoEntity
    ) : MyLocationState()

    data class Error(
        @StringRes val messageId: Int
    ) : MyLocationState()

}
