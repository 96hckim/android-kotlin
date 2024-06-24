package com.hocheol.presentation.main.board

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hocheol.presentation.model.main.board.BoardCardModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun BoardScreen(
    viewModel: BoardViewModel = hiltViewModel()
) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is BoardSideEffect.Toast -> Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
        }
    }

    BoardScreen(
        boardCardModels = state.boardCardModelFlow.collectAsLazyPagingItems(),
        onOptionClick = {},
        onReplyClick = {}
    )
}

@Composable
private fun BoardScreen(
    boardCardModels: LazyPagingItems<BoardCardModel>,
    onOptionClick: (BoardCardModel) -> Unit,
    onReplyClick: (BoardCardModel) -> Unit
) {
    Surface {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                count = boardCardModels.itemCount,
                key = { index -> boardCardModels[index]?.boardId ?: index }
            ) { index ->
                boardCardModels[index]?.run {
                    BoardCard(
                        username = this.username,
                        images = this.images,
                        text = this.text,
                        onOptionClick = { onOptionClick(this) },
                        onReplyClick = { onReplyClick(this) }
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//private fun BoardScreenPreview() {
//    ConnectedTheme {
//        BoardScreen(
//            boardCardModels = listOf(
//                BoardCardModel(
//                    boardId = 1,
//                    username = "user1",
//                    images = listOf("https://via.placeholder.com/150", "https://via.placeholder.com/150"),
//                    text = "This is a sample post text from user1."
//                ),
//                BoardCardModel(
//                    boardId = 2,
//                    username = "user2",
//                    images = listOf("https://via.placeholder.com/150"),
//                    text = "This is another sample post text from user2."
//                ),
//                BoardCardModel(
//                    boardId = 3,
//                    username = "user3",
//                    images = emptyList(),
//                    text = "This is yet another sample post text from user3."
//                )
//            ),
//            onOptionClick = {},
//            onReplyClick = {}
//        )
//    }
//}