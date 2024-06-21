package com.hocheol.presentation.main.writing

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun WritingNavHost(
    onFinish: () -> Unit
) {
    val navController = rememberNavController()
    val sharedViewModel: WritingViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = WritingRoute.IMAGE_SELECT.route
    ) {
        composable(route = WritingRoute.IMAGE_SELECT.route) {
            ImageSelectScreen(
                viewModel = sharedViewModel,
                onBackClick = onFinish,
                onNavigateToWritingScreen = {
                    navController.navigate(WritingRoute.WRITING.route)
                }
            )
        }

        composable(route = WritingRoute.WRITING.route) {
            WritingScreen(
                viewModel = sharedViewModel,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}