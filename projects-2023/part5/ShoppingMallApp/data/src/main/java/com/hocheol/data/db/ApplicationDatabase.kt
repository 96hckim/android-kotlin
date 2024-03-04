package com.hocheol.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hocheol.data.db.dao.BasketDao
import com.hocheol.data.db.dao.LikeDao
import com.hocheol.data.db.dao.PurchaseDao
import com.hocheol.data.db.entity.BasketProductEntity
import com.hocheol.data.db.entity.LikeProductEntity
import com.hocheol.data.db.entity.PurchaseProductEntity

@Database(
    entities = [
        PurchaseProductEntity::class,
        LikeProductEntity::class,
        BasketProductEntity::class
    ],
    version = 1
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun purchaseDao(): PurchaseDao

    abstract fun likeDao(): LikeDao

    abstract fun basketDao(): BasketDao

    companion object {
        const val DB_NAME = "ApplicationDatabase.db"
    }
}