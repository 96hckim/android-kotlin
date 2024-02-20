package com.hocheol.movieapp.ui.components.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hocheol.movieapp.ui.theme.MovieAppTheme
import com.hocheol.movieapp.ui.theme.Paddings

@Composable
fun CategoryRow() {
    Column {
        CategoryTitle("Action")

        LazyRow(
            contentPadding = PaddingValues(
                horizontal = Paddings.large
            )
        ) {
            item {
                MovieItem()
            }
        }
    }
}

@Composable
fun CategoryTitle(genre: String) {
    Text(
        text = "Action",
        modifier = Modifier.padding(
            vertical = Paddings.large,
            horizontal = Paddings.extra
        ),
        style = MaterialTheme.typography.headlineSmall
    )
}

@Preview
@Composable
fun CategoryRowPreview() {
    MovieAppTheme {
        CategoryRow()
    }
}
