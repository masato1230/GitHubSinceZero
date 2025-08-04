package com.github.masato1230.githubclienet.presentation.screens.userdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.masato1230.githubclienet.domain.usecases.GetGitHubUserDetailUseCase
import com.github.masato1230.githubclienet.presentation.AppRoute
import com.github.masato1230.githubclienet.presentation.screens.userdetail.states.UserDetailListItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class UserDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gitHubUserDetailUseCase: GetGitHubUserDetailUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<UserDetailState>(UserDetailState.BaseLoading)
    val state: StateFlow<UserDetailState> = _state
    val userLogin = requireNotNull(savedStateHandle.get<String>(AppRoute.UserDetail.KEY_USER_LOGIN))
    val avatarUrl = requireNotNull(savedStateHandle.get<String>(AppRoute.UserDetail.KEY_AVATAR_URL))

    init {
        loadUserDetail()
    }

    fun onEvent(event: UserDetailEvent) {
        when (event) {
            is UserDetailEvent.OnRetry -> {
                loadUserDetail()
            }
        }
    }

    private fun loadUserDetail() {
        viewModelScope.launch {
            gitHubUserDetailUseCase(login = userLogin).catch {
                Log.e("UserDetailViewModel", "loadUserDetail", it)
                _state.value = UserDetailState.Error
            }.collect {
                _state.value = UserDetailState.ShowList(
                    listItems = UserDetailListItemState.fromModels(it.first),
                    isLoadingCompleted = it.second
                )
            }
        }
    }
}