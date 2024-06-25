package com.hocheol.presentation.main.board

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.map
import com.hocheol.domain.model.Comment
import com.hocheol.domain.usecase.main.board.DeleteBoardUseCase
import com.hocheol.domain.usecase.main.board.DeleteCommentUseCase
import com.hocheol.domain.usecase.main.board.GetBoardsUseCase
import com.hocheol.domain.usecase.main.board.PostCommentUseCase
import com.hocheol.domain.usecase.main.setting.GetMyUserUseCase
import com.hocheol.presentation.model.main.board.BoardCardModel
import com.hocheol.presentation.model.main.board.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val getMyUserUseCase: GetMyUserUseCase,
    private val getBoardsUseCase: GetBoardsUseCase,
    private val deleteBoardUseCase: DeleteBoardUseCase,
    private val postCommentUseCase: PostCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase
) : ViewModel(), ContainerHost<BoardState, BoardSideEffect> {

    override val container: Container<BoardState, BoardSideEffect> = container(
        initialState = BoardState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(BoardSideEffect.Toast(throwable.message.orEmpty()))
                }
            }
        }
    )

    init {
        load()
    }

    fun load() = intent {
        val myUser = getMyUserUseCase().getOrThrow()
        val boardFlow = getBoardsUseCase().getOrThrow()
        val boardCardModelFlow = boardFlow.map { pagingData ->
            pagingData.map { board -> board.toUIModel() }
        }

        reduce {
            state.copy(
                myUserId = myUser.id,
                boardCardModelFlow = boardCardModelFlow
            )
        }
    }

    fun onBoardDelete(model: BoardCardModel) = intent {
        deleteBoardUseCase(model.boardId).getOrThrow()

        reduce {
            state.copy(
                deletedBoardIds = state.deletedBoardIds + model.boardId
            )
        }
    }

    fun onCommentSend(boardId: Long, text: String) = intent {
        val user = getMyUserUseCase().getOrThrow()
        val commentId = postCommentUseCase(boardId = boardId, text = text).getOrThrow()

        val comment = Comment(
            id = commentId,
            text = text,
            userId = user.id,
            username = user.username,
            profileImageUrl = user.profileImageUrl
        )

        val newAddedComments = state.addedComments + Pair(
            boardId,
            state.addedComments[boardId].orEmpty() + comment
        )

        reduce {
            state.copy(
                addedComments = newAddedComments
            )
        }
    }

    fun onDeleteComment(boardId: Long, comment: Comment) = intent {
        deleteCommentUseCase(
            boardId = boardId,
            commentId = comment.id
        ).getOrThrow()

        val newDeletedComments = state.deletedComments + Pair(
            boardId,
            state.deletedComments[boardId].orEmpty() + comment
        )

        reduce {
            state.copy(
                deletedComments = newDeletedComments
            )
        }
    }
}

data class BoardState(
    val myUserId: Long = -1L,
    val boardCardModelFlow: Flow<PagingData<BoardCardModel>> = emptyFlow(),
    val deletedBoardIds: Set<Long> = emptySet(),
    val addedComments: Map<Long, List<Comment>> = emptyMap(),
    val deletedComments: Map<Long, List<Comment>> = emptyMap()
)

sealed interface BoardSideEffect {
    class Toast(val message: String) : BoardSideEffect
}