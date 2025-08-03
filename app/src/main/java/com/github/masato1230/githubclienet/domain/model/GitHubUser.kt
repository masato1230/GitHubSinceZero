package com.github.masato1230.githubclienet.domain.model

data class GitHubUser(
    val id: Int,
    val login: String,
    val avatarUrl: String,
) {

    companion object {

        fun createDummy() = GitHubUser(
            id = 1,
            login = "octocat",
            avatarUrl = "https://picsum.photos/200/200",
        )
    }
}
