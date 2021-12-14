package com.hocheol.delivery.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationLatLngEntity(
    override val id: Long = -1,
    val latitude: Double,
    val longitude: Double
) : Entity, Parcelable
