package com.github.masato1230.githubsincezero.data.entities

import com.github.masato1230.githubsincezero.domain.model.GitHubUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubUserEntity(
    val id: Int,
    val name: String? = null,
    val email: String? = null,
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
) {

    fun toModel(): GitHubUser {
        return GitHubUser(
            id = id,
            login = login,
            avatarUrl = avatarUrl,
        )
    }
}