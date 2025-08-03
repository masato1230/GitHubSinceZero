package com.github.masato1230.githubclienet.domain.model

sealed class GitHubUserSection {

    data class BaseSection(
        val userDetail: GitHubUserDetail,
    ) : GitHubUserSection()

    data class RepositoriesSection(
        val repositories: Result<List<GitHubRepositoryModel>>,
    ) : GitHubUserSection()

    data class EventsSection(
        val events: Result<String>,
    ) : GitHubUserSection()
}