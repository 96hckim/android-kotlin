package com.hocheol.presentation.main.board.comment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hocheol.presentation.component.SNSProfileImage
import com.hocheol.presentation.theme.ConnectedTheme

@Composable
fun CommentCard(
    modifier: Modifier = Modifier,
    profileImageUrl: String? = null,
    username: String = "",
    text: String = "",
    onDeleteComment: () -> Unit
) {
    Surface {
        Row(
            modifier = modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SNSProfileImage(
                modifier = Modifier.size(28.dp),
                profileImageUrl = profileImageUrl,
                borderWidth = 0.7.dp
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = username,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = text,
                    fontSize = 12.sp
                )
            }

            IconButton(onClick = onDeleteComment) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "삭제",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun CommentCardPreview() {
    ConnectedTheme {
        CommentCard(
            username = "User Name",
            text = "Comment",
            onDeleteComment = {}
        )
    }
}