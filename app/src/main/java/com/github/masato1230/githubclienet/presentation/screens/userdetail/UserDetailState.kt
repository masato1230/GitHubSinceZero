package com.github.masato1230.githubclienet.presentation.screens.userdetail

import com.github.masato1230.githubclienet.domain.model.GitHubUserSection

internal sealed class UserDetailState {

    data object BaseLoading : UserDetailState()

    data class ShowList(
        val sections: List<GitHubUserSection>,
    ) : UserDetailState()

    data object Error : UserDetailState()
}