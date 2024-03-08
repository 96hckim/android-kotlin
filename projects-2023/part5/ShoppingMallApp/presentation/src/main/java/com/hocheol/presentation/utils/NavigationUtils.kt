package com.hocheol.presentation.utils

import androidx.navigation.NavHostController
import com.hocheol.presentation.ui.BasketNav
import com.hocheol.presentation.ui.CategoryDetailNav
import com.hocheol.presentation.ui.Destination
import com.hocheol.presentation.ui.MainNav
import com.hocheol.presentation.ui.NavigationRouteName
import com.hocheol.presentation.ui.ProductDetailNav
import com.hocheol.presentation.ui.SearchNav

object NavigationUtils {

    fun navigate(
        controller: NavHostController,
        routeName: String,
        backStackRouteName: String? = null,
        isLaunchSingleTop: Boolean = true,
        needToRestoreState: Boolean = true
    ) {
        controller.navigate(routeName) {
            if (backStackRouteName != null) {
                popUpTo(backStackRouteName) { saveState = true }
            }
            launchSingleTop = isLaunchSingleTop
            restoreState = needToRestoreState
        }
    }

    fun findDestination(route: String?): Destination {
        return when (route) {
            NavigationRouteName.MAIN_HOME -> MainNav.Home
            NavigationRouteName.MAIN_CATEGORY -> MainNav.Category
            NavigationRouteName.MAIN_LIKE -> MainNav.Like
            NavigationRouteName.MAIN_MY_PAGE -> MainNav.MyPage

            CategoryDetailNav.routeWithArgName() -> CategoryDetailNav
            ProductDetailNav.routeWithArgName() -> ProductDetailNav

            NavigationRouteName.SEARCH -> SearchNav
            NavigationRouteName.BASKET -> BasketNav

            else -> MainNav.Home
        }
    }
}