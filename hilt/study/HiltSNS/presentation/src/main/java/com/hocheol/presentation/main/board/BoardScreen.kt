package com.hocheol.presentation.main.board

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hocheol.presentation.model.main.board.BoardCardModel
import com.hocheol.presentation.theme.ConnectedTheme

@Composable
fun BoardScreen(
    boardCardModels: List<BoardCardModel>,
    onOptionClick: (BoardCardModel) -> Unit,
    onReplyClick: (BoardCardModel) -> Unit
) {
    Surface {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                count = boardCardModels.size,
                key = { index -> boardCardModels[index].boardId }
            ) { index ->
                val boardCardModel = boardCardModels[index]

                BoardCard(
                    username = boardCardModel.username,
                    images = boardCardModel.images,
                    text = boardCardModel.text,
                    onOptionClick = { onOptionClick(boardCardModel) },
                    onReplyClick = { onReplyClick(boardCardModel) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun BoardScreenPreview() {
    ConnectedTheme {
        BoardScreen(
            boardCardModels = listOf(
                BoardCardModel(
                    boardId = 1,
                    username = "user1",
                    images = listOf("https://via.placeholder.com/150", "https://via.placeholder.com/150"),
                    text = "This is a sample post text from user1."
                ),
                BoardCardModel(
                    boardId = 2,
                    username = "user2",
                    images = listOf("https://via.placeholder.com/150"),
                    text = "This is another sample post text from user2."
                ),
                BoardCardModel(
                    boardId = 3,
                    username = "user3",
                    images = emptyList(),
                    text = "This is yet another sample post text from user3."
                )
            ),
            onOptionClick = {},
            onReplyClick = {}
        )
    }
}