package com.hocheol.presentation.utils

import android.net.Uri
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.hocheol.domain.model.Category

object NavigationUtils {

    fun navigate(
        navController: NavHostController,
        routeName: String,
        args: Any? = null,
        backStackRouteName: String? = null,
        isLaunchSingleTop: Boolean = true,
        needToRestoreState: Boolean = true
    ) {
        var argument = ""
        if (args != null) {
            when (args) {
                is Category -> {
                    argument = String.format("/%s", Uri.parse(Gson().toJson(args)))
                }
            }
        }
        navController.navigate("$routeName$argument") {
            if (backStackRouteName != null) {
                popUpTo(backStackRouteName) { saveState = true }
            }
            launchSingleTop = isLaunchSingleTop
            restoreState = needToRestoreState
        }
    }
}