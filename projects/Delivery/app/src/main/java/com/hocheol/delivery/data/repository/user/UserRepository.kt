package com.hocheol.delivery.data.repository.user

import com.hocheol.delivery.data.entity.LocationLatLngEntity

interface UserRepository {

    suspend fun getUserLocation(): LocationLatLngEntity?

    suspend fun insertUserLocation(locationLatLngEntity: LocationLatLngEntity)

}