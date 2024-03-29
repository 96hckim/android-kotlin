package com.hocheol.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hocheol.domain.model.SearchKeyword

@Entity(tableName = "search")
data class SearchKeywordEntity(
    @PrimaryKey
    val keyword: String
)

fun SearchKeywordEntity.toDomainModel(): SearchKeyword {
    return SearchKeyword(
        keyword = keyword
    )
}