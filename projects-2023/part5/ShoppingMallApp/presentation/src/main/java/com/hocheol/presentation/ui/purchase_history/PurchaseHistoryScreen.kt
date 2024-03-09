package com.hocheol.presentation.ui.purchase_history

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hocheol.domain.model.BasketProduct
import com.hocheol.domain.model.PurchaseHistory
import com.hocheol.presentation.R
import com.hocheol.presentation.ui.component.Price
import com.hocheol.presentation.utils.NumberUtils
import com.hocheol.presentation.viewmodel.purchase_history.PurchaseHistoryViewModel

@Composable
fun PurchaseHistoryScreen(
    viewModel: PurchaseHistoryViewModel = hiltViewModel()
) {
    val purchaseHistory by viewModel.purchaseHistory.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        purchaseHistory.forEach {
            PurchaseHistoryCard(it)
        }
    }
}

fun LazyListScope.PurchaseHistoryCard(purchaseHistory: PurchaseHistory) {
    item {
        Text(
            fontSize = 16.sp,
            text = "결제 시기 : ${purchaseHistory.purchaseAt}"
        )
    }

    items(purchaseHistory.basketList.size) { index ->
        val currentItem = purchaseHistory.basketList[index]

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.product_image),
                contentDescription = "PurchaseItem",
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = "${currentItem.product.shop.shopName} - ${currentItem.product.productName}",
                    fontSize = 14.sp
                )

                Price(model = currentItem.product)
            }

            Text(
                text = "${currentItem.count} 개",
                modifier = Modifier.padding(start = 30.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }

    item {
        Text(
            text = "${getTotalPrice(purchaseHistory.basketList)} 결제완료",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            fontSize = 16.sp
        )
    }
}

private fun getTotalPrice(list: List<BasketProduct>): String {
    val totalPrice = list.sumOf { it.product.price.finalPrice * it.count }
    return NumberUtils.numberFormatPrice(totalPrice)
}