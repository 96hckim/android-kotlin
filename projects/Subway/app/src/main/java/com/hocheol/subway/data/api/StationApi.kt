package com.hocheol.subway.data.api

import com.hocheol.subway.data.db.entity.StationEntity
import com.hocheol.subway.data.db.entity.SubwayEntity

interface StationApi {

    suspend fun getStationDataUpdatedTimeMillis(): Long

    suspend fun getStationSubways(): List<Pair<StationEntity, SubwayEntity>>

}
