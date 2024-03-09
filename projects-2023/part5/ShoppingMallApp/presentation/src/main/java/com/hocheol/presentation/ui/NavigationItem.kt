package com.hocheol.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.hocheol.domain.model.Category
import com.hocheol.presentation.ui.NavigationRouteName.DEEP_LINK_SCHEME
import com.hocheol.presentation.utils.GsonUtils

sealed class MainNav(
    override val route: String,
    val icon: ImageVector,
    override val title: String
) : Destination {
    data object Home : MainNav(NavigationRouteName.MAIN_HOME, Icons.Filled.Home, NavigationTitle.MAIN_HOME)

    data object Category : MainNav(NavigationRouteName.MAIN_CATEGORY, Icons.Filled.Star, NavigationTitle.MAIN_CATEGORY)

    data object Like : MainNav(NavigationRouteName.MAIN_LIKE, Icons.Filled.Favorite, NavigationTitle.MAIN_LIKE)

    data object MyPage : MainNav(NavigationRouteName.MAIN_MY_PAGE, Icons.Filled.AccountBox, NavigationTitle.MAIN_MY_PAGE)

    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "$DEEP_LINK_SCHEME$route" }
    )

    companion object {
        fun isMainRoute(route: String?): Boolean {
            return when (route) {
                NavigationRouteName.MAIN_HOME, NavigationRouteName.MAIN_CATEGORY,
                NavigationRouteName.MAIN_LIKE, NavigationRouteName.MAIN_MY_PAGE -> true

                else -> false
            }
        }
    }
}

object CategoryDetailNav : DestinationArg<Category> {
    override val route: String = NavigationRouteName.CATEGORY_DETAIL
    override val title: String = NavigationTitle.CATEGORY_DETAIL
    override val argName: String = "category"
    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "$DEEP_LINK_SCHEME$route/{$argName}" }
    )

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(argName) { type = NavType.StringType }
    )

    override fun navigateWithArg(item: Category): String {
        val arg = GsonUtils.toJson(item)
        return "$route/$arg"
    }

    override fun findArgument(navBackStackEntry: NavBackStackEntry): Category? {
        val categoryString = navBackStackEntry.arguments?.getString(argName)
        return GsonUtils.fromJson<Category>(categoryString)
    }
}

object ProductDetailNav : DestinationArg<String> {
    override val route: String = NavigationRouteName.PRODUCT_DETAIL
    override val title: String = NavigationTitle.PRODUCT_DETAIL
    override val argName: String = "productId"
    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "$DEEP_LINK_SCHEME$route/{$argName}" }
    )

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(argName) { type = NavType.StringType }
    )

    override fun navigateWithArg(item: String): String {
        return "$route/$item"
    }

    override fun findArgument(navBackStackEntry: NavBackStackEntry): String? {
        return navBackStackEntry.arguments?.getString(argName)
    }
}

object SearchNav : Destination {
    override val route: String = NavigationRouteName.SEARCH
    override val title: String = NavigationTitle.SEARCH
    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "$DEEP_LINK_SCHEME$route" }
    )
}

object BasketNav : Destination {
    override val route: String = NavigationRouteName.BASKET
    override val title: String = NavigationTitle.BASKET
    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "$DEEP_LINK_SCHEME$route" }
    )
}

object PurchaseHistoryNav : Destination {
    override val route: String = NavigationRouteName.PURCHASE_HISTORY
    override val title: String = NavigationTitle.PURCHASE_HISTORY
    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "$DEEP_LINK_SCHEME$route" }
    )
}

interface Destination {
    val route: String
    val title: String
    val deepLinks: List<NavDeepLink>
}

interface DestinationArg<T> : Destination {
    val argName: String
    val arguments: List<NamedNavArgument>

    fun routeWithArgName() = "$route/{$argName}"
    fun navigateWithArg(item: T): String
    fun findArgument(navBackStackEntry: NavBackStackEntry): T?
}

object NavigationRouteName {
    const val DEEP_LINK_SCHEME = "shoppingmallapp://"
    const val MAIN_HOME = "main_home"
    const val MAIN_CATEGORY = "main_category"
    const val MAIN_MY_PAGE = "main_my_page"
    const val MAIN_LIKE = "main_like"
    const val CATEGORY_DETAIL = "category_detail"
    const val PRODUCT_DETAIL = "product_detail"
    const val SEARCH = "search"
    const val BASKET = "basket"
    const val PURCHASE_HISTORY = "purchase_history"
}

object NavigationTitle {
    const val MAIN_HOME = "홈"
    const val MAIN_CATEGORY = "카테고리"
    const val MAIN_MY_PAGE = "마이페이지"
    const val MAIN_LIKE = "좋아요 모아보기"
    const val CATEGORY_DETAIL = "카테고리별 보기"
    const val PRODUCT_DETAIL = "상품 상세페이지"
    const val SEARCH = "검색"
    const val BASKET = "장바구니"
    const val PURCHASE_HISTORY = "결제내역"
}