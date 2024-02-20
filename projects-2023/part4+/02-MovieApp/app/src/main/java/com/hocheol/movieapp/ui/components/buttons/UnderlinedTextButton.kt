package com.hocheol.movieapp.ui.components.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hocheol.movieapp.ui.theme.MovieAppTheme
import com.hocheol.movieapp.ui.theme.Paddings
import com.hocheol.movieapp.ui.theme.myColorScheme
import com.hocheol.movieapp.ui.theme.underlinedDialogButton

@Composable
fun UnderlinedTextButton(
    modifier: Modifier = Modifier,
    @StringRes id: Int? = null,
    text: String = "",
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.myColorScheme.background,
            contentColor = MaterialTheme.myColorScheme.secondary,
            disabledContentColor = MaterialTheme.myColorScheme.background,
            disabledContainerColor = MaterialTheme.myColorScheme.disabledSecondary
        ),
        elevation = null
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = id?.let { stringResource(id = it) } ?: text,
                style = MaterialTheme.typography.underlinedDialogButton,
                modifier = Modifier.padding(Paddings.small)
            )
        }
    }
}

@Preview
@Composable
fun UnderlinedTextButtonPreview() {
    MovieAppTheme {
        UnderlinedTextButton(
            text = "SUBMIT"
        ) {

        }
    }
}