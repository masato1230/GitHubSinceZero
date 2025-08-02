package com.github.masato1230.githubclienet.presentation

import kotlinx.serialization.Serializable

sealed class AppRoute {

    @Serializable
    data object Home : AppRoute()
}