package com.github.masato1230.githubclienet.presentation

sealed class SharedTransitionKey {

    abstract val key: String

    class UserDetailLogin(
        login: String,
    ) : SharedTransitionKey() {
        override val key: String = "user_detail_login_$login"
    }

    class UserDetailAvatar(
        login: String,
    ) : SharedTransitionKey() {
        override val key: String = "user_detail_avatar_$login"
    }
}