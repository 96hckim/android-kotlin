package com.hocheol.movieapp.ui.components.dialog

import androidx.compose.runtime.Composable
import com.hocheol.movieapp.ui.models.dialog.DialogButton
import com.hocheol.movieapp.ui.models.dialog.DialogContent
import com.hocheol.movieapp.ui.models.dialog.DialogText
import com.hocheol.movieapp.ui.models.dialog.DialogTitle

object DialogPopup

@Composable
fun DialogPopup.Default(
    title: String,
    bodyText: String,
    buttons: List<DialogButton>
) {
    BaseDialogPopup(
        dialogTitle = DialogTitle.Default(title),
        dialogContent = DialogContent.Default(
            DialogText.Default(bodyText)
        ),
        buttons = buttons
    )
}