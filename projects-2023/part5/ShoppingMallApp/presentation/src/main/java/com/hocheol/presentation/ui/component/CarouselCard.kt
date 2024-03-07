package com.hocheol.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hocheol.domain.model.Product
import com.hocheol.presentation.R
import com.hocheol.presentation.model.CarouselVM

@Composable
fun CarouselCard(
    navHostController: NavHostController,
    presentationVM: CarouselVM
) {
    val scrollState = rememberLazyListState()

    Column {
        Text(
            text = presentationVM.model.title,
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
            items(count = presentationVM.model.productList.size) { index ->
                CarouselProductCard(
                    presentationVM = presentationVM,
                    model = presentationVM.model.productList[index]
                ) { product ->
                    presentationVM.openCarouselProduct(navHostController, product)
                }
            }
        }
    }
}

@Composable
private fun CarouselProductCard(
    presentationVM: CarouselVM,
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
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { presentationVM.likeProduct(model) },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    if (model.isLike) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    "FavoriteIcon"
                )
            }

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
}