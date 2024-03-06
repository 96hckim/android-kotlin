package com.hocheol.presentation.delegate

import androidx.navigation.NavHostController
import com.hocheol.domain.model.Category

interface CategoryDelegate {

    fun openCategory(navController: NavHostController, category: Category)
}