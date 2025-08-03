package com.github.masato1230.githubclienet.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.masato1230.githubclienet.domain.model.GitHubUser
import com.github.masato1230.githubclienet.domain.usecases.GetGitHubUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
internal class HiltViewModel @Inject constructor(
    getGitHubUsersUseCase: GetGitHubUsersUseCase,
) : ViewModel() {

    val users: Flow<PagingData<GitHubUser>> = getGitHubUsersUseCase().cachedIn(viewModelScope)
}