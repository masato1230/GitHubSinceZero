package com.github.masato1230.githubclienet.domain.model

sealed class GitHubUserAdditionalInfo {

    data class Base(
        val userDetail: GitHubUserDetail,
    ) : GitHubUserAdditionalInfo()

    data class Repositories(
        val repositories: Result<List<GitHubRepositoryModel>>,
    ) : GitHubUserAdditionalInfo()

    data class Events(
        val events: Result<List<GitHubEvent>>,
    ) : GitHubUserAdditionalInfo()
}