package com.hocheol.delivery.screen.main.home

import androidx.annotation.StringRes
import com.hocheol.delivery.data.entity.location.MapSearchInfoEntity

sealed class HomeState {

    object UnInitialized : HomeState()

    object Loading : HomeState()

    data class Success(
        val mapSearchInfo: MapSearchInfoEntity,
        val isLocationSame: Boolean
    ) : HomeState()

    data class Error(
        @StringRes val messageId: Int
    ) : HomeState()

}
