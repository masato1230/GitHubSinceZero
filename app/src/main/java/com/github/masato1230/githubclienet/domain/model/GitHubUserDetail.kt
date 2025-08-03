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
) {

    companion object {

        fun createDummy(): GitHubUserDetail {
            return GitHubUserDetail(
                login = "login",
                name = "name",
                company = "company",
                blog = "blog",
                location = "location",
                email = "email",
                hireable = true,
                bio = "bio",
                twitterUsername = "twitterUsername",
                publicRepos = 1,
                publicGists = 2,
                followers = 3,
                following = 4
            )
        }
    }
}