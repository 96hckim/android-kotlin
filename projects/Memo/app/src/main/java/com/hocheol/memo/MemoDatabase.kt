package com.hocheol.memo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(MemoEntity::class), version = 1)
abstract class MemoDatabase : RoomDatabase() {

    abstract fun memoDAO(): MemoDAO

    companion object {
        var INSTANCE: MemoDatabase? = null

        fun getInstance(context: Context): MemoDatabase? {
            if (INSTANCE == null) {
                synchronized(MemoDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MemoDatabase::class.java,
                        "memo.db"
                    )
                        .fallbackToDestructiveMigration() // 버전 변경 시 데이터 드랍
                        .build()
                }
            }

            return INSTANCE
        }
    }
}