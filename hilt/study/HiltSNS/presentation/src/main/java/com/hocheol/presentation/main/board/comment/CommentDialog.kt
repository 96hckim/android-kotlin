package com.hocheol.presentation.main.board.comment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hocheol.domain.model.Comment
import com.hocheol.presentation.component.SNSTextField
import com.hocheol.presentation.theme.ConnectedTheme

@Composable
fun CommentDialog(
    visible: Boolean,
    comments: List<Comment>,
    onDismissRequest: () -> Unit,
    onCloseClick: () -> Unit,
    onSendClick: (String) -> Unit,
    onDeleteComment: (Comment) -> Unit
) {
    if (visible) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            var commentText by remember { mutableStateOf("") }

            DialogContainer {
                CommentContent(
                    comments = comments,
                    onCloseClick = onCloseClick,
                    commentText = commentText,
                    onCommentTextChange = { commentText = it },
                    onSendClick = {
                        onSendClick(commentText)
                        commentText = ""
                    },
                    onDeleteComment = onDeleteComment
                )
            }
        }
    }
}

@Composable
private fun DialogContainer(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            content()
        }
    }
}

// Extracted composable for the comment content
@Composable
private fun CommentContent(
    comments: List<Comment>,
    onCloseClick: () -> Unit,
    commentText: String,
    onCommentTextChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onDeleteComment: (Comment) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxHeight(0.5f)
    ) {
        CommentHeader(
            commentCount = comments.size,
            onCloseClick = onCloseClick
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(
                count = comments.size
            ) { index ->
                val comment = comments[index]

                CommentCard(
                    modifier = Modifier,
                    profileImageUrl = comment.profileImageUrl,
                    username = comment.username,
                    text = comment.text,
                    onDeleteComment = { onDeleteComment(comment) }
                )
            }
        }

        HorizontalDivider()

        CommentInputRow(
            commentText = commentText,
            onCommentTextChange = onCommentTextChange,
            onSendClick = onSendClick
        )
    }
}

@Composable
private fun CommentHeader(
    commentCount: Int,
    onCloseClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$commentCount 댓글",
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = onCloseClick) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "닫기"
            )
        }
    }
}

@Composable
private fun CommentInputRow(
    commentText: String,
    onCommentTextChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        SNSTextField(
            modifier = Modifier.weight(1f),
            value = commentText,
            onValueChange = onCommentTextChange
        )

        IconButton(onClick = onSendClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "전송"
            )
        }
    }
}

@Preview
@Composable
private fun CommentDialogPreview() {
    ConnectedTheme {
        CommentDialog(
            visible = true,
            comments = emptyList(),
            onDismissRequest = {},
            onCloseClick = {},
            onSendClick = {},
            onDeleteComment = {}
        )
    }
}