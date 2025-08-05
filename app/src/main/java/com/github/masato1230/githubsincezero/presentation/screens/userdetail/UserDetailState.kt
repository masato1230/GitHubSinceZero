package com.github.masato1230.githubsincezero.presentation.screens.userdetail

import com.github.masato1230.githubsincezero.presentation.screens.userdetail.states.UserDetailListItemState

internal sealed class UserDetailState {

    data object BaseLoading : UserDetailState()

    data class ShowList(
        val listItems: List<UserDetailListItemState>,
        val isLoadingCompleted: Boolean,
    ) : UserDetailState()

    data class Error(
        val e: Throwable,
    ) : UserDetailState()
}