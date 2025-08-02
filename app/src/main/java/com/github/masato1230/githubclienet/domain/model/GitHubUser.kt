package com.github.masato1230.githubclienet.domain.model

data class GitHubUser(
    val id: Int,
    val name: String,
    val avatarUrl: String,
) {

    companion object {

        fun createDummy() = GitHubUser(
            id = 1,
            name = "John Doe",
            avatarUrl = "https://picsum.photos/200/200",
        )
    }
}