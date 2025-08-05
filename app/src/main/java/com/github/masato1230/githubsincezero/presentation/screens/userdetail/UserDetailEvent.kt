package com.github.masato1230.githubsincezero.presentation.screens.userdetail

internal sealed interface UserDetailEvent {

    data object OnRetry : UserDetailEvent
}