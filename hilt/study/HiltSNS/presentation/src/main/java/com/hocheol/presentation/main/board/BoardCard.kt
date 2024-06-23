package com.hocheol.presentation.main.board

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hocheol.presentation.component.SNSImagePager
import com.hocheol.presentation.theme.ConnectedTheme

@Composable
fun BoardCard(
    profileImageUrl: String? = null,
    username: String,
    images: List<String>,
    text: String,
    onOptionClick: () -> Unit,
    onReplyClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            BoardHeader(
                modifier = Modifier.fillMaxWidth(),
                profileImageUrl = profileImageUrl,
                username = username,
                onOptionClick = onOptionClick
            )

            if (images.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))

                SNSImagePager(
                    modifier = Modifier.fillMaxWidth(),
                    images = images
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            var maxLines by remember(text) { mutableIntStateOf(1) }
            var showMore by remember { mutableStateOf(false) }

            Text(
                text = text,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
                maxLines = maxLines,
                onTextLayout = { textLayoutResult ->
                    showMore = textLayoutResult.didOverflowHeight
                }
            )

            if (showMore) {
                TextButton(
                    onClick = { maxLines = Integer.MAX_VALUE }
                ) {
                    Text(
                        text = "더보기",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            TextButton(
                onClick = onReplyClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "댓글")
            }
        }
    }
}

@Preview
@Composable
private fun BoardCardPreview() {
    ConnectedTheme {
        BoardCard(
            profileImageUrl = null,
            username = "User Name",
            images = emptyList(),
            text = "내용1\n내용2\n내용3",
            onOptionClick = {},
            onReplyClick = {}
        )
    }
}