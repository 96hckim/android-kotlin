package com.hocheol.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hocheol.domain.model.Category
import com.hocheol.domain.model.Product
import com.hocheol.domain.model.SalesStatus
import com.hocheol.domain.model.Shop
import com.hocheol.presentation.R
import com.hocheol.presentation.delegate.ProductDelegate
import com.hocheol.presentation.model.ProductVM
import com.hocheol.presentation.ui.theme.ShoppingMallAppTheme

@Composable
fun ProductCard(
    navHostController: NavHostController,
    presentationVM: ProductVM
) {
    Card(
        onClick = { presentationVM.openProduct(navHostController, presentationVM.model) },
        modifier = Modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Max)
            .padding(10.dp)
            .shadow(elevation = 10.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { presentationVM.likeProduct(presentationVM.model) },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    if (presentationVM.model.isLike) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
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
                    fontWeight = FontWeight.SemiBold,
                    text = presentationVM.model.shop.shopName
                )

                Text(
                    fontSize = 14.sp,
                    text = presentationVM.model.productName
                )

                Price(model = presentationVM.model)
            }
        }
    }
}

@Composable
fun Price(model: Product) {
    when (model.price.salesStatus) {
        SalesStatus.ON_SALE -> {
            Text(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                text = "${model.price.originPrice}원"
            )
        }

        SalesStatus.ON_DISCOUNT -> {
            Text(
                fontSize = 14.sp,
                text = "${model.price.originPrice}원",
                style = TextStyle(textDecoration = TextDecoration.LineThrough)
            )

            Text(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error,
                text = "${model.price.finalPrice}원"
            )
        }

        SalesStatus.SOLD_OUT -> {
            Text(
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.tertiary,
                text = "판매종료"
            )
        }
    }
}

@Preview
@Composable
private fun ProductCardSalePreview() {
    ShoppingMallAppTheme {
        ProductCard(
            rememberNavController(),
            ProductVM(
                model = Product(
                    productId = "1",
                    productName = "상품 이름",
                    imageUrl = "",
                    price = com.hocheol.domain.model.Price(
                        30000,
                        30000,
                        SalesStatus.ON_SALE,
                    ),
                    category = Category.Top,
                    shop = Shop(
                        "1",
                        "샵 이름",
                        "",
                    ),
                    isNew = false,
                    isFreeShipping = false,
                    isLike = true
                ),
                productDelegate = object : ProductDelegate {
                    override fun openProduct(navController: NavHostController, product: Product) {
                    }

                    override fun likeProduct(product: Product) {
                    }
                }
            )
        )
    }
}

@Preview
@Composable
private fun ProductCardDisCountPreview() {
    ShoppingMallAppTheme {
        ProductCard(
            rememberNavController(),
            ProductVM(
                model = Product(
                    productId = "1",
                    productName = "상품 이름",
                    imageUrl = "",
                    price = com.hocheol.domain.model.Price(
                        30000,
                        20000,
                        SalesStatus.ON_DISCOUNT,
                    ),
                    category = Category.Top,
                    shop = Shop(
                        "1",
                        "샵 이름",
                        "",
                    ),
                    isNew = false,
                    isFreeShipping = false,
                    isLike = false
                ),
                productDelegate = object : ProductDelegate {
                    override fun openProduct(navController: NavHostController, product: Product) {
                    }

                    override fun likeProduct(product: Product) {
                    }
                }
            )
        )
    }
}

@Preview
@Composable
private fun ProductCardSoldOutPreview() {
    ShoppingMallAppTheme {
        ProductCard(
            rememberNavController(),
            ProductVM(
                model = Product(
                    productId = "1",
                    productName = "상품 이름",
                    imageUrl = "",
                    price = com.hocheol.domain.model.Price(
                        30000,
                        30000,
                        SalesStatus.SOLD_OUT,
                    ),
                    category = Category.Top,
                    shop = Shop(
                        "1",
                        "샵 이름",
                        "",
                    ),
                    isNew = false,
                    isFreeShipping = false,
                    isLike = false
                ),
                productDelegate = object : ProductDelegate {
                    override fun openProduct(navController: NavHostController, product: Product) {
                    }

                    override fun likeProduct(product: Product) {
                    }
                }
            )
        )
    }
}