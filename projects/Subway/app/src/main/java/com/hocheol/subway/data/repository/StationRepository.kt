package com.hocheol.subway.data.repository

import com.hocheol.subway.domain.ArrivalInformation
import com.hocheol.subway.domain.Station
import kotlinx.coroutines.flow.Flow

interface StationRepository {

    val stations: Flow<List<Station>>

    suspend fun refreshStations()

//    suspend fun getStationArrivals(stationName: String): List<ArrivalInformation>
//
//    suspend fun updateStation(station: Station)

}
