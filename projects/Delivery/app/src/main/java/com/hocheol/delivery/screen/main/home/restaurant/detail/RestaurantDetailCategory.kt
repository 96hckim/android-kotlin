package com.hocheol.delivery.screen.main.home.restaurant.detail

import androidx.annotation.StringRes
import com.hocheol.delivery.R

enum class RestaurantDetailCategory(
    @StringRes val categoryNameId: Int
) {

    MENU(R.string.menu), REVIEW(R.string.review)

}