package com.hocheol.presentation.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hocheol.presentation.R
import com.hocheol.presentation.model.BannerListVM
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerListCard(
    presentationVM: BannerListVM
) {
    val pagerState = rememberPagerState { presentationVM.model.imageList.size }

    LaunchedEffect(key1 = pagerState) {
        autoScrollInfinity(pagerState)
    }

    HorizontalPager(state = pagerState) { page ->
        Card(
            onClick = { presentationVM.openBannerList(presentationVM.model.bannerId) },
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

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                Text(text = "page : $page")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private suspend fun autoScrollInfinity(pagerState: PagerState) {
    while (true) {
        delay(3000)
        val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
        pagerState.animateScrollToPage(nextPage)
    }
}