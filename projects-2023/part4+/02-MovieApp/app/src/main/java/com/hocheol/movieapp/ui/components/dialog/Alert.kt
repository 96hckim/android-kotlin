package com.hocheol.movieapp.ui.components.dialog

import androidx.compose.runtime.Composable
import com.hocheol.movieapp.ui.models.dialog.DialogButton
import com.hocheol.movieapp.ui.models.dialog.DialogContent
import com.hocheol.movieapp.ui.models.dialog.DialogText
import com.hocheol.movieapp.ui.models.dialog.DialogTitle

@Composable
fun DialogPopup.Alert(
    title: String,
    bodyText: String,
    buttons: List<DialogButton>
) {
    BaseDialogPopup(
        dialogTitle = DialogTitle.Header(
            title
        ),
        dialogContent = DialogContent.Large(
            DialogText.Default(
                bodyText
            )
        ),
        buttons = buttons
    )
}