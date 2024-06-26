package com.hocheol.presentation.model.main.board

import androidx.compose.runtime.Immutable
import com.hocheol.domain.model.Board
import com.hocheol.domain.model.Comment
import com.mohamedrejeb.richeditor.model.RichTextState

@Immutable
data class BoardCardModel(
    val boardId: Long,
    val richTextState: RichTextState,
    val images: List<String>,
    val userId: Long,
    val username: String,
    val profileImageUrl: String? = null,
    val comments: List<Comment>
)

fun Board.toUIModel(): BoardCardModel {
    return BoardCardModel(
        boardId = id,
        richTextState = RichTextState().setHtml(content),
        images = images,
        userId = userId,
        username = username,
        profileImageUrl = profileImageUrl,
        comments = comments
    )
}