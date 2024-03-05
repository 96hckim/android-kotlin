package com.hocheol.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hocheol.domain.model.Banner
import com.hocheol.presentation.R

@Composable
fun BannerCard(model: Banner) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .shadow(20.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.banner_image),
            contentDescription = "BannerImage",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f),
            contentScale = ContentScale.Crop
        )
    }
}