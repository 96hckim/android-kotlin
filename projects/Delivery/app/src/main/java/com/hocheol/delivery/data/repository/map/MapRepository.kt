package com.hocheol.delivery.data.repository.map

import com.hocheol.delivery.data.entity.LocationLatLngEntity
import com.hocheol.delivery.data.response.address.AddressInfo

interface MapRepository {

    suspend fun getReverseGeoInformation(
        locationLatLngEntity: LocationLatLngEntity
    ): AddressInfo?

}