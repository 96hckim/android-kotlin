package com.hocheol.presentation.model.main.board

import androidx.compose.runtime.Immutable
import com.hocheol.domain.model.Board
import com.hocheol.domain.model.Comment

@Immutable
data class BoardCardModel(
    val boardId: Long,
    val username: String,
    val images: List<String>,
    val text: String,
    val comments: List<Comment>
)

fun Board.toUIModel(): BoardCardModel {
    return BoardCardModel(
        boardId = id,
        username = username,
        images = images,
        text = content,
        comments = comments
    )
}