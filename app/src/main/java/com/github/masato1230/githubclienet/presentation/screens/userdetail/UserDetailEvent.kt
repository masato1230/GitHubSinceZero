package com.github.masato1230.githubclienet.presentation.screens.userdetail

internal sealed interface UserDetailEvent {

    data object OnRetry : UserDetailEvent
}