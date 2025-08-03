package com.github.masato1230.githubclienet.domain.model

class GitHubUserDetail(
    val login: String,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    val hireable: Boolean?,
    val bio: String?,
    val twitterUsername: String?,
    val publicRepos: Int,
    val publicGists: Int,
    val followers: Int,
    val following: Int
)