package com.github.masato1230.githubsincezero.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.masato1230.githubsincezero.domain.model.GitHubUser
import com.github.masato1230.githubsincezero.domain.usecases.GetGitHubUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
internal class HiltViewModel @Inject constructor(
    getGitHubUsersUseCase: GetGitHubUsersUseCase,
) : ViewModel() {

    val users: Flow<PagingData<GitHubUser>> = getGitHubUsersUseCase().cachedIn(viewModelScope)
}