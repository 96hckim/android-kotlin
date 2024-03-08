package com.hocheol.data.repository

import com.hocheol.data.db.dao.LikeDao
import com.hocheol.data.db.entity.toDomainModel
import com.hocheol.domain.model.Product
import com.hocheol.domain.repository.LikeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LikeRepositoryImpl @Inject constructor(
    private val likeDao: LikeDao
) : LikeRepository {

    override fun getLikeProducts(): Flow<List<Product>> {
        return likeDao.getAll().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
}