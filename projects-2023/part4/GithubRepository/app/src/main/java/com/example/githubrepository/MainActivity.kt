package com.example.githubrepository

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.githubrepository.ui.theme.GithubRepositoryTheme
import com.example.githubrepository.viewmodel.GithubViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubRepositoryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ReposScreen()
                }
            }
        }
    }
}

@Composable
fun ReposScreen(viewModel: GithubViewModel = viewModel()) {
    val isLoading by viewModel.isLoading
    LazyColumn {
        item {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = viewModel.userId,
                    onValueChange = viewModel.setUserId,
                    label = {
                        Text(text = "아이디")
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                Button(
                    onClick = { viewModel.getRepos() },
                    enabled = isLoading.not()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text(
                            text = "검색",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
        items(
            items = viewModel.repos,
            key = { it.id }
        ) {
            Text(it.name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GithubRepositoryTheme {
        ReposScreen()
    }
}