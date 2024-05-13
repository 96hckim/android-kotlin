package com.hocheol.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hocheol.hiltsns.ui.theme.HiltSNSTheme
import com.hocheol.presentation.component.SNSButton
import com.hocheol.presentation.component.SNSTextField

@Composable
fun SignUpScreen(
    id: String,
    username: String,
    password1: String,
    password2: String,

    onIdChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPassword1Change: (String) -> Unit,
    onPassword2Change: (String) -> Unit,

    onSignUpClick: () -> Unit
) {
    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(top = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Connected",
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = "Your favorite social network",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxHeight()
            ) {
                Text(
                    text = "Create an account",
                    modifier = Modifier.padding(top = 36.dp),
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = "Id",
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.labelLarge
                )
                SNSTextField(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    value = id,
                    onValueChange = onIdChange
                )

                Text(
                    text = "Username",
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.labelLarge
                )
                SNSTextField(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    value = username,
                    onValueChange = onUsernameChange
                )

                Text(
                    text = "Password",
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.labelLarge
                )
                SNSTextField(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    value = password1,
                    onValueChange = onPassword1Change
                )

                Text(
                    text = "Repeat password",
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.labelLarge
                )
                SNSTextField(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    value = password2,
                    onValueChange = onPassword2Change
                )

                SNSButton(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth(),
                    text = "Sign up",
                    onClick = onSignUpClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    HiltSNSTheme {
        SignUpScreen(
            id = "admin",
            username = "khc",
            password1 = "1234",
            password2 = "1234",
            onIdChange = {},
            onUsernameChange = {},
            onPassword1Change = {},
            onPassword2Change = {},
            onSignUpClick = {}
        )
    }
}