package com.github.masato1230.githubclienet.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
data class GitHubRepositoryEntity(
    val id: Int,
    @SerialName("node_id") val nodeId: String,
    val name: String,
    @SerialName("full_name")
    val fullName: String,
    val owner: GitHubOwnerDto,
    val description: String?,
    @SerialName("fork")
    val isFork: Boolean,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
    @SerialName("watchers_count")
    val watchersCount: Int,
    @SerialName("forks_count")
    val forksCount: Int,
    @SerialName("open_issues_count")
    val openIssuesCount: Int,
    @SerialName("default_branch")
    val defaultBranch: String,
    val language: String?,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("pushed_at")
    val pushedAt: String
) {
    @Serializable
    data class GitHubOwnerDto(
        val id: Int,
        val login: String,
        @SerialName("node_id") val nodeId: String,
        @SerialName("avatar_url") val avatarUrl: String,
    )
}

