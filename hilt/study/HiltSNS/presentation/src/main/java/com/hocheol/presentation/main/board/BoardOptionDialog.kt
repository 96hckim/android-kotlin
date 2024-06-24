package com.hocheol.presentation.main.board

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.hocheol.presentation.model.main.board.BoardCardModel
import com.hocheol.presentation.theme.ConnectedTheme

@Composable
fun BoardOptionDialog(
    model: BoardCardModel?,
    onDismissRequest: () -> Unit,
    onBoardDelete: (BoardCardModel) -> Unit
) {
    if (model != null) {
        Dialog(onDismissRequest = onDismissRequest) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(
                    onClick = {
                        onBoardDelete(model)
                        onDismissRequest()
                    },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(text = "삭제")
                }
            }
        }
    }
}

@Preview
@Composable
private fun BoardOptionDialogPreview() {
    var model: BoardCardModel? by remember {
        mutableStateOf(
            BoardCardModel(
                boardId = 1,
                username = "Sample User Name",
                images = emptyList(),
                text = "Sample Text"
            )
        )
    }

    ConnectedTheme {
        BoardOptionDialog(
            model = model,
            onDismissRequest = { model = null },
            onBoardDelete = {}
        )
    }
}