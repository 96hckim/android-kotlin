package com.hocheol.presentation.main.writing.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hocheol.presentation.theme.ConnectedTheme
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState

@Composable
fun WritingToolbar(
    modifier: Modifier = Modifier,
    richTextState: RichTextState
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
            )
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (toolbarType in WritingToolbarType.entries) {
            WritingToolbarButton(
                toolbarType = toolbarType,
                richTextState = richTextState
            )
        }
    }
}

@Composable
private fun WritingToolbarButton(
    toolbarType: WritingToolbarType,
    richTextState: RichTextState
) {
    IconButton(
        onClick = {
            richTextState.toggleSpanStyle(toolbarType.toSpanStyle())
        }
    ) {
        Icon(
            painter = painterResource(id = toolbarType.resId),
            contentDescription = toolbarType.name,
            modifier = Modifier.size(20.dp),
            tint = if (richTextState.hasSpanStyle(toolbarType)) {
                MaterialTheme.colorScheme.onSurface
            } else {
                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            }
        )
    }
}

@Preview
@Composable
private fun WritingToolbarPreview() {
    ConnectedTheme {
        Surface {
            val richTextState = rememberRichTextState()

            WritingToolbar(
                modifier = Modifier.fillMaxWidth(),
                richTextState = richTextState
            )
        }
    }
}