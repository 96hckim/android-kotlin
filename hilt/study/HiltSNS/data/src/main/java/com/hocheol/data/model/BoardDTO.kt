package com.hocheol.data.model

import com.hocheol.data.model.comment.CommentDTO
import com.hocheol.data.model.comment.toDomainModel
import com.hocheol.domain.model.Board
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class BoardDTO(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String,
    val createUserId: Long,
    val createUserName: String,
    val createUserProfileFilePath: String,
    val commentList: List<CommentDTO>
)

fun BoardDTO.toDomainModel(): Board {
    val contentParam = Json.decodeFromString<ContentParam>(content)

    return Board(
        id = id,
        title = title,
        content = contentParam.text,
        images = contentParam.images,
        username = createUserName,
        profileImageUrl = createUserProfileFilePath,
        comments = commentList.map { it.toDomainModel() }
    )
}