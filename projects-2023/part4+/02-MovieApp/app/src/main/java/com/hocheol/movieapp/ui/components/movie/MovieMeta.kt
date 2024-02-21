package com.hocheol.movieapp.ui.components.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hocheol.movieapp.ui.theme.Paddings
import com.hocheol.movieapp.ui.theme.myColorScheme
import com.hocheol.movieapp.ui.util.getAnnotatedText

@Composable
fun MovieMeta(
    modifier: Modifier = Modifier,
    key: String,
    value: String
) {
    Column(modifier = modifier) {
        // Key
        Text(
            text = key,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.myColorScheme.onSurface.copy(alpha = 0.5f)
        )

        // Value
        Text(
            text = getAnnotatedText(text = value),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(Paddings.large))
    }
}