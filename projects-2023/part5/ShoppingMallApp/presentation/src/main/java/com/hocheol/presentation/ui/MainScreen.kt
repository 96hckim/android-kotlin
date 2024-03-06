package com.hocheol.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.hocheol.domain.model.Category
import com.hocheol.presentation.ui.category.CategoryDetailScreen
import com.hocheol.presentation.ui.main.CategoryScreen
import com.hocheol.presentation.ui.main.HomeScreen
import com.hocheol.presentation.ui.product_detail.ProductDetailScreen
import com.hocheol.presentation.ui.search.SearchScreen
import com.hocheol.presentation.ui.theme.ShoppingMallAppTheme
import com.hocheol.presentation.viewmodel.MainViewModel

@Composable
fun MainScreen() {
    val viewModel = hiltViewModel<MainViewModel>()
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (NavigationItem.MainNav.isMainRoute(currentRoute)) {
                MainHeader(
                    viewModel = viewModel,
                    navController = navController
                )
            }
        },
        bottomBar = {
            if (NavigationItem.MainNav.isMainRoute(currentRoute)) {
                MainBottomNavigationBar(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
        ) {
            MainNavigationScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainHeader(
    viewModel: MainViewModel,
    navController: NavHostController
) {
    TopAppBar(
        title = { Text("My App") },
        actions = {
            IconButton(
                onClick = {
                    viewModel.openSearchForm(navController)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "SearchIcon"
                )
            }
        }
    )
}


@Composable
fun MainBottomNavigationBar(
    navController: NavHostController,
    currentRoute: String?
) {
    val bottomNavigationItems = listOf(
        NavigationItem.MainNav.Home,
        NavigationItem.MainNav.Category,
        NavigationItem.MainNav.MyPage
    )

    NavigationBar {
        bottomNavigationItems.forEach { item ->
            NavigationBarItem(
                selected = item.route == currentRoute,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.route
                    )
                }
            )
        }
    }
}

@Composable
fun MainNavigationScreen(
    viewModel: MainViewModel,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = NavigationRouteName.MAIN_HOME) {
        composable(NavigationRouteName.MAIN_HOME) {
            HomeScreen(navHostController = navController, viewModel = viewModel)
        }

        composable(NavigationRouteName.MAIN_CATEGORY) {
            CategoryScreen(viewModel = viewModel, navController)
        }

        composable(NavigationRouteName.MAIN_MY_PAGE) {
            Text(text = "Hello MyPage")
        }

        composable(
            route = NavigationRouteName.CATEGORY_DETAIL + "/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val jsonString = navBackStackEntry.arguments?.getString("category")
            val category = Gson().fromJson(jsonString, Category::class.java)
            if (category != null) {
                CategoryDetailScreen(navHostController = navController, category = category)
            }
        }

        composable(
            route = NavigationRouteName.PRODUCT_DETAIL + "/{product}",
            arguments = listOf(navArgument("product") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val productId = navBackStackEntry.arguments?.getString("product")
            if (productId != null) {
                ProductDetailScreen(productId)
            }
        }

        composable(NavigationRouteName.SEARCH) {
            SearchScreen(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ShoppingMallAppTheme {
        MainScreen()
    }
}