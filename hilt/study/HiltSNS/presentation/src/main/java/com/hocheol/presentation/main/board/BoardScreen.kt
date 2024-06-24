package com.hocheol.presentation.main.board

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hocheol.presentation.model.main.board.BoardCardModel
import com.hocheol.presentation.theme.ConnectedTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun BoardScreen(
    viewModel: BoardViewModel
) {
    val context = LocalContext.current
    val state = viewModel.collectAsState().value
    var modelForDialog: BoardCardModel? by remember { mutableStateOf(null) }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is BoardSideEffect.Toast -> Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
        }
    }

    BoardScreen(
        boardCardModels = state.boardCardModelFlow.collectAsLazyPagingItems(),
        deletedBoardIds = state.deletedBoardIds,
        onOptionClick = { modelForDialog = it },
        onReplyClick = {}
    )

    BoardOptionDialog(
        model = modelForDialog,
        onDismissRequest = { modelForDialog = null },
        onBoardDelete = viewModel::onBoardDelete
    )
}

@Composable
private fun BoardScreen(
    boardCardModels: LazyPagingItems<BoardCardModel>,
    deletedBoardIds: Set<Long> = emptySet(),
    onOptionClick: (BoardCardModel) -> Unit,
    onReplyClick: (BoardCardModel) -> Unit
) {
    Surface {
        if (boardCardModels.itemCount == 0) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "아직 게시글이 없습니다.\n새로운 게시글을 작성해보세요!",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    count = boardCardModels.itemCount,
                    key = { index -> boardCardModels[index]?.boardId ?: index }
                ) { index ->
                    boardCardModels[index]?.run {
                        if (!deletedBoardIds.contains(this.boardId)) {
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
    }
}

private fun mockPagingData(): Flow<PagingData<BoardCardModel>> {
    val data = listOf(
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
    )
    return flowOf(PagingData.from(data))
}

@Preview
@Composable
private fun BoardScreenPreview() {
    val mockDataFlow = mockPagingData()
    val lazyPagingItems = mockDataFlow.collectAsLazyPagingItems()

    ConnectedTheme {
        BoardScreen(
            boardCardModels = lazyPagingItems,
            onOptionClick = {},
            onReplyClick = {}
        )
    }
}