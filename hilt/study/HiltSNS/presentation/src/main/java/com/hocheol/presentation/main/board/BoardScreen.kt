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
import com.hocheol.domain.model.Comment
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
        myUserId = state.myUserId,
        boardCardModels = state.boardCardModelFlow.collectAsLazyPagingItems(),
        deletedBoardIds = state.deletedBoardIds,
        addedComments = state.addedComments,
        deletedComments = state.deletedComments,
        onOptionClick = { modelForDialog = it },
        onCommentSend = viewModel::onCommentSend,
        onDeleteComment = viewModel::onDeleteComment
    )

    BoardOptionDialog(
        model = modelForDialog,
        onDismissRequest = { modelForDialog = null },
        onBoardDelete = viewModel::onBoardDelete
    )
}

@Composable
private fun BoardScreen(
    myUserId: Long,
    boardCardModels: LazyPagingItems<BoardCardModel>,
    deletedBoardIds: Set<Long>,
    addedComments: Map<Long, List<Comment>>,
    deletedComments: Map<Long, List<Comment>>,
    onOptionClick: (BoardCardModel) -> Unit,
    onCommentSend: (Long, String) -> Unit,
    onDeleteComment: (Long, Comment) -> Unit
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
                    boardCardModels[index]?.let { model ->
                        if (!deletedBoardIds.contains(model.boardId)) {
                            BoardCard(
                                isMine = model.userId == myUserId,
                                boardId = model.boardId,
                                username = model.username,
                                images = model.images,
                                text = model.text,
                                comments = model.comments + addedComments[model.boardId].orEmpty() - deletedComments[model.boardId].orEmpty().toSet(),
                                onOptionClick = { onOptionClick(model) },
                                onCommentSend = onCommentSend,
                                onDeleteComment = onDeleteComment
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
            userId = 1L,
            boardId = 1,
            username = "user1",
            images = listOf("https://via.placeholder.com/150", "https://via.placeholder.com/150"),
            text = "This is a sample post text from user1.",
            comments = emptyList()
        ),
        BoardCardModel(
            userId = 2L,
            boardId = 2,
            username = "user2",
            images = emptyList(),
            text = "This is yet another sample post text from user2.",
            comments = emptyList()
        ),
        BoardCardModel(
            userId = 3L,
            boardId = 3,
            username = "user3",
            images = listOf("https://via.placeholder.com/150"),
            text = "This is another sample post text from user3.",
            comments = emptyList()
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
            myUserId = 0L,
            boardCardModels = lazyPagingItems,
            deletedBoardIds = emptySet(),
            addedComments = emptyMap(),
            deletedComments = emptyMap(),
            onOptionClick = {},
            onCommentSend = { _, _ -> },
            onDeleteComment = { _, _ -> }
        )
    }
}