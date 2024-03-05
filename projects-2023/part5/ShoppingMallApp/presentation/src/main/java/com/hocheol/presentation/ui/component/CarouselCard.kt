package com.hocheol.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hocheol.domain.model.Carousel
import com.hocheol.domain.model.Product
import com.hocheol.presentation.R

@Composable
fun CarouselCard(
    model: Carousel,
    onClick: (Product) -> Unit
) {
    val scrollState = rememberLazyListState()

    Column {
        Text(
            text = model.title,
            modifier = Modifier.padding(10.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

        LazyRow(
            state = scrollState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            items(count = model.productList.size) { index ->
                CarouselProductCard(model = model.productList[index], onClick)
            }
        }
    }
}

@Composable
private fun CarouselProductCard(
    model: Product,
    onClick: (Product) -> Unit
) {
    Card(
        onClick = { onClick(model) },
        modifier = Modifier
            .width(150.dp)
            .wrapContentHeight()
            .padding(10.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.product_image),
                contentDescription = "ProductImage",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Text(
                fontSize = 14.sp,
                text = model.productName
            )

            Price(model = model)
        }
    }
}