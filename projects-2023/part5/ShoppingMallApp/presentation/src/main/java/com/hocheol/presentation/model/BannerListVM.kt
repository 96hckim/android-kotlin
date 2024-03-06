package com.hocheol.presentation.model

import com.hocheol.domain.model.BannerList
import com.hocheol.presentation.delegate.BannerDelegate

class BannerListVM(
    model: BannerList,
    private val bannerDelegate: BannerDelegate
) : PresentationVM<BannerList>(model) {

    fun openBannerList(bannerId: String) {
        bannerDelegate.openBanner(bannerId)
    }
}