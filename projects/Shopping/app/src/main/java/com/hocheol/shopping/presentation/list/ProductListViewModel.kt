package com.hocheol.shopping.presentation.list

import androidx.lifecycle.viewModelScope
import com.hocheol.shopping.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ProductListViewModel : BaseViewModel() {

    override fun fetchData(): Job = viewModelScope.launch {
    }

}