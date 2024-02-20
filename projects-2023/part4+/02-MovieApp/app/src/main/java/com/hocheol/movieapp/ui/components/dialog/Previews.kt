package com.hocheol.movieapp.ui.components.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hocheol.movieapp.R
import com.hocheol.movieapp.ui.models.buttons.LeadingIconData
import com.hocheol.movieapp.ui.models.dialog.DialogButton
import com.hocheol.movieapp.ui.theme.MovieAppTheme

@Preview
@Composable
fun AlertPreview() {
    MovieAppTheme {
        DialogPopup.Alert(
            title = "Title",
            bodyText = "blah blah blah",
            buttons = listOf(
                DialogButton.UnderlinedText("Okay") {}
            )
        )
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MovieAppTheme {
        DialogPopup.Default(
            title = "Title",
            bodyText = "blah blah blah",
            buttons = listOf(
                DialogButton.Primary("OPEN") {},
                DialogButton.SecondaryBorderless("CANCEL") {}
            )
        )
    }
}

@Preview
@Composable
fun RatingPreview() {
    MovieAppTheme {
        DialogPopup.Rating(
            movieName = "The Lord of the Rings: The Two Towers",
            rating = 2.5f,
            buttons = listOf(
                DialogButton.Primary(
                    title = "OPEN",
                    leadingIconData = LeadingIconData(
                        iconDrawable = R.drawable.baseline_send_24,
                        iconContentDescription = null
                    )
                ) {},
                DialogButton.SecondaryBorderless("CANCEL") {}
            )
        )
    }
}