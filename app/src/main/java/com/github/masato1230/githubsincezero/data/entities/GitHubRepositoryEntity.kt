package com.github.masato1230.githubsincezero.data.entities

import com.github.masato1230.githubsincezero.data.serializers.ZonedDateTimeSerializer
import com.github.masato1230.githubsincezero.domain.model.GitHubRepositoryModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Serializable
data class GitHubRepositoryEntity(
    val id: Int,
    @SerialName("node_id") val nodeId: String,
    val name: String,
    @SerialName("full_name")
    val fullName: String,
    val owner: GitHubOwnerEntity,
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
    @Serializable(with = ZonedDateTimeSerializer::class)
    val updatedAt: ZonedDateTime,
    @SerialName("html_url")
    val htmlUrl: String,
) {

    fun toModel(): GitHubRepositoryModel {
        return  GitHubRepositoryModel(
            id = id,
            fullName = fullName,
            description = description,
            stargazersCount = stargazersCount,
            watchersCount = watchersCount,
            forksCount = forksCount,
            openIssuesCount = openIssuesCount,
            language = language,
            updatedAt = updatedAt,
            htmlUrl = htmlUrl,
        )
    }
}

