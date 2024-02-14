package com.hocheol.imageextraction.mvi

sealed class MviIntent {
    data object LoadImage : MviIntent()
}