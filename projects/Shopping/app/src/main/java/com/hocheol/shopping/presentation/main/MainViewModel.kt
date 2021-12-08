package com.hocheol.shopping.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hocheol.shopping.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class MainViewModel : BaseViewModel() {

    private var _mainStateLiveData = MutableLiveData<MainState>()
    val mainStateLiveData: LiveData<MainState> = _mainStateLiveData

    override fun fetchData(): Job = viewModelScope.launch { }

    fun refreshOrderList() {
        _mainStateLiveData.postValue(MainState.RefreshOrderList)
    }

}