package com.hocheol.delivery.screen.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hocheol.delivery.R
import com.hocheol.delivery.data.entity.LocationLatLngEntity
import com.hocheol.delivery.data.entity.MapSearchInfoEntity
import com.hocheol.delivery.data.repository.map.MapRepository
import com.hocheol.delivery.data.repository.user.UserRepository
import com.hocheol.delivery.screen.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val mapRepository: MapRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    companion object {
        const val MY_LOCATION_KEY = "MyLocation"
    }

    val homeStateLiveData = MutableLiveData<HomeState>(HomeState.UnInitialized)

    fun loadReverseGeoInformation(
        locationLatLngEntity: LocationLatLngEntity
    ) = viewModelScope.launch {
        homeStateLiveData.value = HomeState.Loading
        val userLocation = userRepository.getUserLocation()
        val currentLocation = userLocation ?: locationLatLngEntity

        val addressInfo = mapRepository.getReverseGeoInformation(currentLocation)
        addressInfo?.let { info ->
            homeStateLiveData.value = HomeState.Success(
                mapSearchInfo = info.toSearchInfoEntity(locationLatLngEntity),
                isLocationSame = currentLocation == locationLatLngEntity
            )
        } ?: kotlin.run {
            homeStateLiveData.value = HomeState.Error(
                R.string.cannot_load_address_info
            )
        }
    }

    fun getMapSearchInfo(): MapSearchInfoEntity? {
        return when (val data = homeStateLiveData.value) {
            is HomeState.Success -> {
                data.mapSearchInfo
            }
            else -> {
                null
            }
        }
    }

}