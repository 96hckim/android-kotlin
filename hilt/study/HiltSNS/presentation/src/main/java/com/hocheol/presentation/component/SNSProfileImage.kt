package com.hocheol.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun SNSProfileImage(
    modifier: Modifier = Modifier,
    profileImageUrl: String? = null,
    borderWidth: Dp = 4.dp
) {
    Box {
        val rainbowColorsBrush = remember {
            Brush.sweepGradient(
                listOf(
                    Color(0xFF9575CD),
                    Color(0xFFBA68C8),
                    Color(0xFFE57373),
                    Color(0xFFFFB74D),
                    Color(0xFFFFF176),
                    Color(0xFFAED581),
                    Color(0xFF4DD0E1),
                    Color(0xFF9575CD)
                )
            )
        }
        Image(
            painter = if (!profileImageUrl.isNullOrEmpty()) {
                rememberAsyncImagePainter(
                    model = profileImageUrl,
                    contentScale = ContentScale.Crop
                )
            } else {
                rememberVectorPainter(image = Icons.Filled.Person)
            },
            contentDescription = "프로필 사진",
            modifier = modifier
                .border(
                    border = BorderStroke(borderWidth, rainbowColorsBrush),
                    shape = CircleShape
                )
                .padding(borderWidth)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            colorFilter = if (profileImageUrl.isNullOrEmpty()) {
                ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            } else {
                null
            }
        )
    }
}