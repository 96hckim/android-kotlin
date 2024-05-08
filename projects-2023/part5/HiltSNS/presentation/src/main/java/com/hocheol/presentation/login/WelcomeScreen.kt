package com.hocheol.presentation.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hocheol.hiltsns.ui.theme.HiltSNSTheme
import com.hocheol.presentation.component.SNSButton

@Composable
fun WelcomeScreen() {
    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.padding(top = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Connected",
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    text = "Your favorite social network",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            SNSButton(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                text = "로그인",
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun WelcomeScreenPreview() {
    HiltSNSTheme {
        WelcomeScreen()
    }
}