package com.github.masato1230.githubsincezero.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class GitHubOwnerEntity(
    val login: String,
)