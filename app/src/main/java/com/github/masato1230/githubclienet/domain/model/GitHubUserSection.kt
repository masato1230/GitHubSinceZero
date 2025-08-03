package com.github.masato1230.githubclienet.domain.model

sealed class GitHubUserSection {

    data class BaseSection(
        val userDetail: GitHubUserDetail,
    ) : GitHubUserSection()
}