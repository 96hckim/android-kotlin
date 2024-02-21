package com.hocheol.movieapp.features.feed.presentation.output

sealed class FeedState {
    data object Loading : FeedState()

    class Main(
        val categories: List<CategoryEntity>
    ) : FeedState()

    class Failed(
        val reason: String
    ) : FeedState()
}