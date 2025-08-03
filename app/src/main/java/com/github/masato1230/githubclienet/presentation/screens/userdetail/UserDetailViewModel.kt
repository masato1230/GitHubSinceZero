package com.github.masato1230.githubclienet.presentation.screens.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.masato1230.githubclienet.presentation.AppRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class UserDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val userLogin = requireNotNull(savedStateHandle.get<String>(AppRoute.UserDetail.KEY_USER_LOGIN))
    val avatarUrl = requireNotNull(savedStateHandle.get<String>(AppRoute.UserDetail.KEY_AVATAR_URL))
}