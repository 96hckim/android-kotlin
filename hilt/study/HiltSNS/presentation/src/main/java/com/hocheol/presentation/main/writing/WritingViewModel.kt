package com.hocheol.presentation.main.writing

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.hocheol.domain.model.Image
import com.hocheol.domain.usecase.main.writing.GetImageListUseCase
import com.hocheol.domain.usecase.main.writing.PostBoardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class WritingViewModel @Inject constructor(
    private val getImageListUseCase: GetImageListUseCase,
    private val postBoardUseCase: PostBoardUseCase
) : ViewModel(), ContainerHost<WritingState, WritingSideEffect> {

    override val container: Container<WritingState, WritingSideEffect> = container(
        initialState = WritingState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(WritingSideEffect.Toast(throwable.message.orEmpty()))
                }
            }
        }
    )

    init {
        load()
    }

    private fun load() = intent {
        val images = getImageListUseCase()
        reduce {
            state.copy(
                images = images,
                selectedImages = images.firstOrNull()?.let { listOf(it) } ?: emptyList()
            )
        }
    }

    fun onItemClick(image: Image) = intent {
        reduce {
            val newSelectedImages = if (state.selectedImages.contains(image)) {
                state.selectedImages - image
            } else {
                state.selectedImages + image
            }
            state.copy(selectedImages = newSelectedImages)
        }
    }

    fun onNextClick() = intent {
        if (state.selectedImages.isEmpty()) {
            postSideEffect(WritingSideEffect.Toast(message = "이미지를 선택해 주세요."))
        } else {
            postSideEffect(WritingSideEffect.NavigateToWritingScreen)
        }
    }

    fun onTextChange(newText: String) = blockingIntent {
        reduce {
            state.copy(text = newText)
        }
    }

    fun onPostClick() = intent {
        postBoardUseCase(
            title = "제목없음",
            content = state.text,
            images = state.selectedImages
        )

        postSideEffect(WritingSideEffect.Finish)
    }
}

@Immutable
data class WritingState(
    val images: List<Image> = emptyList(),
    val selectedImages: List<Image> = emptyList(),
    val text: String = ""
)

sealed interface WritingSideEffect {
    class Toast(val message: String) : WritingSideEffect

    data object NavigateToWritingScreen : WritingSideEffect

    data object Finish : WritingSideEffect
}