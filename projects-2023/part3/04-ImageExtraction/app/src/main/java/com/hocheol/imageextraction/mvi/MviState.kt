package com.hocheol.imageextraction.mvi

import com.hocheol.imageextraction.mvi.model.Image

sealed class MviState {
    data object Idle : MviState()
    data object Loading : MviState()
    data class LoadedImage(
        val image: Image,
        val count: Int
    ) : MviState()
}