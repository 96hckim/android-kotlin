package com.hocheol.sweettracker.data.repository

import com.hocheol.sweettracker.data.entity.TrackingInformation
import com.hocheol.sweettracker.data.entity.TrackingItem
import kotlinx.coroutines.flow.Flow

interface TrackingItemRepository {

//    val trackingItems: Flow<List<TrackingItem>>

    suspend fun getTrackingItemInformation(): List<Pair<TrackingItem, TrackingInformation>>

//    suspend fun getTrackingInformation(companyCode: String, invoice: String): TrackingInformation?
//
//    suspend fun saveTrackingItem(trackingItem: TrackingItem)
//
//    suspend fun deleteTrackingItem(trackingItem: TrackingItem)

}
