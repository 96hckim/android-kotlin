package com.hocheol.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.hocheol.presentation.ui.basket.BasketScreen
import com.hocheol.presentation.ui.category.CategoryDetailScreen
import com.hocheol.presentation.ui.main.CategoryScreen
import com.hocheol.presentation.ui.main.HomeScreen
import com.hocheol.presentation.ui.main.LikeScreen
import com.hocheol.presentation.ui.main.MyPageScreen
import com.hocheol.presentation.ui.product_detail.ProductDetailScreen
import com.hocheol.presentation.ui.purchase_history.PurchaseHistoryScreen
import com.hocheol.presentation.ui.search.SearchScreen
import com.hocheol.presentation.utils.NavigationUtils
import com.hocheol.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(googleSignInClient: GoogleSignInClient) {
    val viewModel = hiltViewModel<MainViewModel>()
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            MainHeader(
                viewModel = viewModel,
                navController = navController,
                currentRoute = currentRoute
            )
        },
        bottomBar = {
            if (MainNav.isMainRoute(currentRoute)) {
                MainBottomNavigationBar(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) { data ->
                Snackbar(
                    snackbarData = data,
                    modifier = Modifier.padding(50.dp),
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
        ) {
            MainNavigationScreen(
                viewModel = viewModel,
                navController = navController,
                googleSignInClient = googleSignInClient,
                snackbarHostState = snackbarHostState
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainHeader(
    viewModel: MainViewModel,
    navController: NavHostController,
    currentRoute: String?
) {
    TopAppBar(
        title = { Text(NavigationUtils.findDestination(currentRoute).title) },
        navigationIcon = {
            if (MainNav.isMainRoute(currentRoute).not()) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "BackIcon")
                }
            }
        },
        actions = {
            if (MainNav.isMainRoute(currentRoute)) {
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

                IconButton(
                    onClick = {
                        viewModel.openBasket(navController)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "ShoppingCartIcon"
                    )
                }
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
        MainNav.Home,
        MainNav.Category,
        MainNav.MyPage,
        MainNav.Like
    )

    NavigationBar {
        bottomNavigationItems.forEach { item ->
            NavigationBarItem(
                selected = item.route == currentRoute,
                onClick = {
                    NavigationUtils.navigate(
                        controller = navController,
                        routeName = item.route,
                        backStackRouteName = navController.graph.startDestinationRoute
                    )
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
    navController: NavHostController,
    googleSignInClient: GoogleSignInClient,
    snackbarHostState: SnackbarHostState
) {
    NavHost(navController = navController, startDestination = MainNav.Home.route) {
        composable(
            route = MainNav.Home.route,
            deepLinks = MainNav.Home.deepLinks
        ) {
            HomeScreen(navHostController = navController, viewModel = viewModel)
        }

        composable(
            route = MainNav.Category.route,
            deepLinks = MainNav.Category.deepLinks
        ) {
            CategoryScreen(viewModel = viewModel, navController = navController)
        }

        composable(
            route = MainNav.Like.route,
            deepLinks = MainNav.Like.deepLinks
        ) {
            LikeScreen(navHostController = navController, viewModel = viewModel)
        }

        composable(
            route = MainNav.MyPage.route,
            deepLinks = MainNav.MyPage.deepLinks
        ) {
            MyPageScreen(viewModel = viewModel, googleSignInClient = googleSignInClient, navHostController = navController)
        }

        composable(
            route = CategoryDetailNav.routeWithArgName(),
            arguments = CategoryDetailNav.arguments,
            deepLinks = CategoryDetailNav.deepLinks
        ) { navBackStackEntry ->
            val category = CategoryDetailNav.findArgument(navBackStackEntry)
            if (category != null) {
                CategoryDetailScreen(navHostController = navController, category = category)
            }
        }

        composable(
            route = ProductDetailNav.routeWithArgName(),
            arguments = ProductDetailNav.arguments,
            deepLinks = ProductDetailNav.deepLinks
        ) { navBackStackEntry ->
            val productId = ProductDetailNav.findArgument(navBackStackEntry)
            if (productId != null) {
                ProductDetailScreen(productId = productId)
            }
        }

        composable(
            route = SearchNav.route,
            deepLinks = SearchNav.deepLinks
        ) {
            SearchScreen(navHostController = navController)
        }

        composable(
            route = BasketNav.route,
            deepLinks = BasketNav.deepLinks
        ) {
            BasketScreen(snackbarHostState = snackbarHostState)
        }

        composable(
            route = PurchaseHistoryNav.route,
            deepLinks = PurchaseHistoryNav.deepLinks
        ) {
            PurchaseHistoryScreen()
        }
    }
}

fun popupSnackBar(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    message: String,
    onDismissCallback: () -> Unit = {}
) {
    scope.launch {
        snackbarHostState.showSnackbar(message)
        onDismissCallback()
    }
}