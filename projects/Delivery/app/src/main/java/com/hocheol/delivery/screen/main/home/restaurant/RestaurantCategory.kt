package com.hocheol.delivery.screen.main.home.restaurant

import androidx.annotation.StringRes
import com.hocheol.delivery.R

enum class RestaurantCategory(
    @StringRes val categoryNameId: Int,
    @StringRes val categoryTypeId: Int
) {

    ALL(R.string.all, R.string.all_type)

}