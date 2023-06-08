package com.example.githubrepository.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepository.model.Repo
import com.example.githubrepository.service.GithubService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val githubService: GithubService
) : ViewModel() {
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    var userId by mutableStateOf("")
    val repos = mutableStateListOf<Repo>()

    val setUserId: (String) -> Unit = {
        userId = it
    }

    fun getRepos() = viewModelScope.launch {
        _isLoading.value = true
        repos.clear()
        val result = githubService.listRepos(userId)
        repos.addAll(result)
        _isLoading.value = false
    }
}
