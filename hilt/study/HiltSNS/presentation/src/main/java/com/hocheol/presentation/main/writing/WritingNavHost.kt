package com.hocheol.presentation.main.writing

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun WritingNavHost(
    onFinish: () -> Unit
) {
    val navController = rememberNavController()
    val sharedViewModel: WritingViewModel = viewModel()
    val context = LocalContext.current

    sharedViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is WritingSideEffect.Toast -> Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            WritingSideEffect.NavigateToWritingScreen -> navController.navigate(WritingRoute.WRITING.route)
            WritingSideEffect.Finish -> onFinish()
        }
    }

    NavHost(
        navController = navController,
        startDestination = WritingRoute.IMAGE_SELECT.route
    ) {
        composable(route = WritingRoute.IMAGE_SELECT.route) {
            ImageSelectScreen(
                viewModel = sharedViewModel,
                onBackClick = onFinish
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