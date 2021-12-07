package com.hocheol.shopping.presentation.main

import androidx.lifecycle.viewModelScope
import com.hocheol.shopping.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class MainViewModel : BaseViewModel() {

    override fun fetchData(): Job = viewModelScope.launch {
    }

}