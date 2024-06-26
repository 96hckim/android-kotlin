package com.hocheol.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hocheol.data.model.comment.CommentDTO
import com.hocheol.data.model.comment.toDomainModel
import com.hocheol.domain.model.Board
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Entity
@Serializable
data class BoardDTO(
    @PrimaryKey val id: Long,
    val title: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String,
    val createUserId: Long,
    val createUserName: String,
    val createUserProfileFilePath: String? = null,
    val commentList: List<CommentDTO>
)

fun BoardDTO.toDomainModel(): Board {
    val contentParam = Json.decodeFromString<ContentParam>(content)

    return Board(
        id = id,
        title = title,
        content = contentParam.text,
        images = contentParam.images,
        userId = createUserId,
        username = createUserName,
        profileImageUrl = createUserProfileFilePath,
        comments = commentList.map { it.toDomainModel() }
    )
}