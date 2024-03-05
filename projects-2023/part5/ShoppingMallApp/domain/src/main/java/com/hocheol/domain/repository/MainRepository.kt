package com.hocheol.domain.repository

import com.hocheol.domain.model.BaseModel
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getModelList(): Flow<List<BaseModel>>
}