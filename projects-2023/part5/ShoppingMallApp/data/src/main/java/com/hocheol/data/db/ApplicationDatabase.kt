package com.hocheol.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hocheol.data.db.dao.BasketDao
import com.hocheol.data.db.dao.LikeDao
import com.hocheol.data.db.dao.PurchaseDao
import com.hocheol.data.db.dao.PurchaseHistoryDao
import com.hocheol.data.db.dao.SearchDao
import com.hocheol.data.db.entity.BasketProductEntity
import com.hocheol.data.db.entity.LikeProductEntity
import com.hocheol.data.db.entity.PurchaseHistoryEntity
import com.hocheol.data.db.entity.PurchaseProductEntity
import com.hocheol.data.db.entity.SearchKeywordEntity

@Database(
    entities = [
        PurchaseProductEntity::class,
        LikeProductEntity::class,
        BasketProductEntity::class,
        SearchKeywordEntity::class,
        PurchaseHistoryEntity::class
    ],
    version = 1
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun purchaseDao(): PurchaseDao

    abstract fun likeDao(): LikeDao

    abstract fun basketDao(): BasketDao

    abstract fun searchDao(): SearchDao

    abstract fun purchaseHistoryDao(): PurchaseHistoryDao

    companion object {
        const val DB_NAME = "ApplicationDatabase.db"
    }
}