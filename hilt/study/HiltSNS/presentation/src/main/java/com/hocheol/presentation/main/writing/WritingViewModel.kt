package com.hocheol.presentation.main.writing

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.hocheol.domain.model.Image
import com.hocheol.domain.usecase.main.writing.GetImageListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class WritingViewModel @Inject constructor(
    private val getImageListUseCase: GetImageListUseCase
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
}

@Immutable
data class WritingState(
    val images: List<Image> = emptyList(),
    val selectedImages: List<Image> = emptyList()
)

sealed interface WritingSideEffect {
    class Toast(val message: String) : WritingSideEffect
}