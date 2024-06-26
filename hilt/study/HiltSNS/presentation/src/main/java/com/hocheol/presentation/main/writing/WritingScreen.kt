package com.hocheol.presentation.main.writing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import com.hocheol.presentation.component.SNSImagePager
import com.hocheol.presentation.main.writing.toolbar.WritingToolbar
import com.hocheol.presentation.theme.ConnectedTheme
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.BasicRichTextEditor
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun WritingScreen(
    viewModel: WritingViewModel,
    onBackClick: () -> Unit
) {
    val state = viewModel.collectAsState().value

    WritingScreen(
        richTextState = state.richTextState,
        images = state.selectedImages.map { it.uri },
        onBackClick = onBackClick,
        onPostClick = viewModel::onPostClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WritingScreen(
    richTextState: RichTextState,
    images: List<String>,
    onBackClick: () -> Unit,
    onPostClick: () -> Unit
) {
    Surface {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "새 게시물",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "뒤로가기"
                            )
                        }
                    },
                    actions = {
                        TextButton(onClick = onPostClick) {
                            Text(text = "게시")
                        }
                    }
                )
            },
            bottomBar = {
                WritingToolbar(
                    modifier = Modifier.fillMaxWidth(),
                    richTextState = richTextState
                )
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    SNSImagePager(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f),
                        images = images
                    )

                    HorizontalDivider()

                    BasicRichTextEditor(
                        state = richTextState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(3f),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                        decorationBox = { innerTextField ->
                            if (richTextState.annotatedString.isEmpty()) {
                                Text(text = "문구를 입력해 주세요.")
                            }
                            innerTextField()
                        }
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun WritingScreenPreview() {
    ConnectedTheme {
        WritingScreen(
            richTextState = RichTextState(),
            images = emptyList(),
            onBackClick = {},
            onPostClick = {}
        )
    }
}