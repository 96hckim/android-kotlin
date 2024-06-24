package com.hocheol.data.model

import com.hocheol.domain.model.Board
import kotlinx.serialization.Serializable

@Serializable
data class BoardDTO(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String,
    val createUserId: Long,
    val createUserName: String,
    val createUserProfileFilePath: String
)

fun BoardDTO.toDomainModel(): Board {
    return Board(
        id = id,
        title = title,
        content = content,
        images = emptyList(),
        username = createUserName,
        profileImageUrl = createUserProfileFilePath
    )
}