package com.hocheol.presentation.model

import com.hocheol.domain.model.Banner
import com.hocheol.presentation.delegate.BannerDelegate

class BannerVM(
    model: Banner,
    bannerDelegate: BannerDelegate
) : PresentationVM<Banner>(model), BannerDelegate by bannerDelegate