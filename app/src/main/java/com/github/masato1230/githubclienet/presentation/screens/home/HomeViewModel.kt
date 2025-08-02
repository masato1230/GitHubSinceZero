package com.github.masato1230.githubclienet.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.masato1230.githubclienet.data.repositories.githubusers.GitHubUsersRepository
import com.github.masato1230.githubclienet.domain.model.GitHubUser
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
internal class HiltViewModel @Inject constructor(
    private val gitHubUsersRepository: GitHubUsersRepository,
) : ViewModel() {

    private val _users = mutableStateOf<List<GitHubUser>>(emptyList())
    val users: State<List<GitHubUser>> = _users

    init {
        loadSample()
    }

    fun loadSample() = viewModelScope.launch {
        val users = gitHubUsersRepository.fetchUsers(since = 1)
        _users.value = users
    }
}