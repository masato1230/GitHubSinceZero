package com.github.masato1230.githubclienet.presentation.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.masato1230.githubclienet.data.repositories.githubusers.GitHubUsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
internal class HiltViewModel @Inject constructor(
    private val gitHubUsersRepository: GitHubUsersRepository,
) : ViewModel() {

    init {
        loadSample()
    }

    private val _sampleText = mutableStateOf("Loading")
    val sampleText = _sampleText

    fun loadSample() = viewModelScope.launch {
        val sampleText = gitHubUsersRepository.fetchUsers(
            since = 1,
        )
        _sampleText.value = sampleText
    }
}