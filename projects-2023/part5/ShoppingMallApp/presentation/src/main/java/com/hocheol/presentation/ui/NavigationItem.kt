package com.hocheol.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.hocheol.domain.model.Category
import com.hocheol.presentation.ui.NavigationRouteName.CATEGORY_DETAIL
import com.hocheol.presentation.ui.NavigationRouteName.MAIN_CATEGORY
import com.hocheol.presentation.ui.NavigationRouteName.MAIN_HOME
import com.hocheol.presentation.ui.NavigationRouteName.MAIN_MY_PAGE

sealed class NavigationItem(open val route: String) {
    sealed class MainNav(override val route: String, val icon: ImageVector, val name: String) : NavigationItem(route) {
        data object Home : MainNav(MAIN_HOME, Icons.Filled.Home, MAIN_HOME)
        data object Category : MainNav(MAIN_CATEGORY, Icons.Filled.Star, MAIN_CATEGORY)
        data object MyPage : MainNav(MAIN_MY_PAGE, Icons.Filled.AccountBox, MAIN_MY_PAGE)

        companion object {
            fun isMainRoute(route: String?): Boolean {
                return when (route) {
                    MAIN_HOME, MAIN_CATEGORY, MAIN_MY_PAGE -> true
                    else -> false
                }
            }
        }
    }

    data class CategoryNav(val category: Category) : NavigationItem(CATEGORY_DETAIL)
}

object NavigationRouteName {
    const val MAIN_HOME = "main_home"
    const val MAIN_CATEGORY = "main_category"
    const val MAIN_MY_PAGE = "main_my_page"
    const val CATEGORY_DETAIL = "category_detail"
}