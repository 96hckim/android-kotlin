package com.hocheol.movieapp.features.feed.presentation.output

import com.hocheol.movieapp.features.common.entity.CategoryEntity

sealed class FeedState {
    data object Loading : FeedState()

    class Main(
        val categories: List<CategoryEntity>
    ) : FeedState()

    class Failed(
        val reason: String
    ) : FeedState()
}