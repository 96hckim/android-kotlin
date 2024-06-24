package com.hocheol.presentation.main.board

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.map
import com.hocheol.domain.usecase.main.board.GetBoardsUseCase
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
    private val getBoardsUseCase: GetBoardsUseCase
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

    private fun load() = intent {
        val boardFlow = getBoardsUseCase().getOrThrow()
        val boardCardModelFlow = boardFlow.map { pagingData ->
            pagingData.map { board -> board.toUIModel() }
        }

        reduce {
            state.copy(boardCardModelFlow = boardCardModelFlow)
        }
    }
}

data class BoardState(
    val boardCardModelFlow: Flow<PagingData<BoardCardModel>> = emptyFlow()
)

sealed interface BoardSideEffect {
    class Toast(val message: String) : BoardSideEffect
}