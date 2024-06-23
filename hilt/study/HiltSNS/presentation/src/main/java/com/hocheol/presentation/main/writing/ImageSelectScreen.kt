package com.hocheol.presentation.main.writing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.hocheol.domain.model.Image
import com.hocheol.presentation.theme.ConnectedTheme
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ImageSelectScreen(
    viewModel: WritingViewModel,
    onBackClick: () -> Unit
) {
    val state = viewModel.collectAsState().value

    ImageSelectScreen(
        images = state.images,
        selectedImages = state.selectedImages,
        onBackClick = onBackClick,
        onNextClick = viewModel::onNextClick,
        onItemClick = viewModel::onItemClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ImageSelectScreen(
    images: List<Image>,
    selectedImages: List<Image>,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onItemClick: (Image) -> Unit
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
                        TextButton(onClick = onNextClick) {
                            Text(text = "다음")
                        }
                    }
                )
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        if (selectedImages.isEmpty()) {
                            Text(
                                text = "선택된 이미지가 없습니다.",
                                textAlign = TextAlign.Center
                            )
                        } else {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = selectedImages.lastOrNull()?.uri
                                ),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(110.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(MaterialTheme.colorScheme.onBackground),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        items(
                            count = images.size,
                            key = { index ->
                                images[index].uri
                            }
                        ) { index ->
                            val image = images[index]
                            val isSelected = selectedImages.contains(image)

                            Box(
                                modifier = Modifier.clickable { onItemClick(image) }
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = image.uri,
                                        contentScale = ContentScale.Crop
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f),
                                    contentScale = ContentScale.Crop
                                )

                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Filled.CheckCircle,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .align(Alignment.TopStart)
                                            .padding(4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun ImageSelectScreenPreview() {
    ConnectedTheme {
        ImageSelectScreen(
            images = emptyList(),
            selectedImages = emptyList(),
            onBackClick = {},
            onNextClick = {},
            onItemClick = {}
        )
    }
}