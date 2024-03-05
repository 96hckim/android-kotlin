package com.hocheol.presentation.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hocheol.domain.model.Product
import com.hocheol.domain.model.Ranking
import com.hocheol.presentation.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RankingCard(
    model: Ranking,
    onClick: (Product) -> Unit
) {
    val pagerState = rememberPagerState {
        model.productList.size / DEFAULT_RANKING_ITEM_COUNT
    }

    Column {
        Text(
            text = model.title,
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(end = 50.dp)
        ) { page ->
            Column {
                repeat(DEFAULT_RANKING_ITEM_COUNT) { count ->
                    val index = page * DEFAULT_RANKING_ITEM_COUNT + count
                    RankingProductCard(index, model.productList[index], onClick)
                }
            }
        }
    }
}

@Composable
fun RankingProductCard(
    index: Int,
    model: Product,
    onClick: (Product) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "${index + 1}",
            modifier = Modifier.padding(end = 10.dp),
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterResource(id = R.drawable.product_image),
            contentDescription = "RankingImage",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(80.dp)
                .aspectRatio(0.7f)
                .clickable { onClick(model) }
        )

        Column(
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Text(
                text = model.shop.shopName,
                modifier = Modifier.padding(top = 10.dp),
                fontSize = 14.sp
            )

            Text(
                text = model.productName,
                modifier = Modifier.padding(top = 10.dp),
                fontSize = 14.sp
            )

            Price(model = model)
        }
    }
}

private const val DEFAULT_RANKING_ITEM_COUNT = 3