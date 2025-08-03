package com.github.masato1230.githubclienet.presentation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class AppRoute {

    @Serializable
    data object Home : AppRoute()

    @Serializable
    data class UserDetail(
        @SerialName(KEY_USER_LOGIN)
        val userLogin: String,
        @SerialName(KEY_AVATAR_URL)
        val avatarUrl: String,
    ) : AppRoute() {

        companion object {
            const val KEY_USER_LOGIN = "userLogin"
            const val KEY_AVATAR_URL = "avatarUrl"
        }
    }
}