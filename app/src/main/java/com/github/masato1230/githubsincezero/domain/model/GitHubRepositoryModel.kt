package com.github.masato1230.githubsincezero.domain.model

import java.time.ZonedDateTime

data class GitHubRepositoryModel(
    val id: Int,
    val fullName: String,
    val description: String?,
    val stargazersCount: Int,
    val watchersCount: Int,
    val forksCount: Int,
    val openIssuesCount: Int,
    val language: String?,
    val updatedAt: ZonedDateTime,
    val htmlUrl: String,
) {

    companion object {
        fun createDummy(): GitHubRepositoryModel {
            return GitHubRepositoryModel(
                id = 1,
                fullName = "dummy full name",
                description = "dummy description",
                stargazersCount = 1,
                watchersCount = 1,
                forksCount = 1,
                openIssuesCount = 1,
                language = "dummy language",
                updatedAt = ZonedDateTime.now(),
                htmlUrl = "https://dummy.url",
            )
        }
    }
}