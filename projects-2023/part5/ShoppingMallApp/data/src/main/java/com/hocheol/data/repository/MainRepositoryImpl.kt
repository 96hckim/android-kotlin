package com.hocheol.data.repository

import com.hocheol.data.datasource.ProductDataSource
import com.hocheol.domain.model.BaseModel
import com.hocheol.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource
) : MainRepository {

    override fun getModelList(): Flow<List<BaseModel>> {
        return productDataSource.getHomeComponents()
    }
}