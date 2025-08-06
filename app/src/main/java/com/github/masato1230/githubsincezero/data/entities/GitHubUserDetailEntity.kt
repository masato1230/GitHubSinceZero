package com.github.masato1230.githubsincezero.data.entities

import com.github.masato1230.githubsincezero.domain.model.GitHubUserDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubUserDetailEntity(
    val id: Int,
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    val name: String?,
    val company: String?,
    val blog: String,
    val location: String?,
    val email: String?,
    val bio: String?,
    @SerialName("twitter_username")
    val twitterUsername: String?,
    @SerialName("public_repos")
    val publicRepos: Int,
    @SerialName("public_gists")
    val publicGists: Int,
    val followers: Int,
    val following: Int,
    @SerialName("repos_url")
    val reposUrl: String,
    @SerialName("events_url")
    val eventsUrl: String,
) {

    fun toModel(): GitHubUserDetail {
        return GitHubUserDetail(
            login = login,
            name = name,
            company = company,
            blog = blog.takeIf { it.isNotBlank() },
            location = location,
            email = email,
            bio = bio,
            twitterUsername = twitterUsername,
            publicRepos = publicRepos,
            publicGists = publicGists,
            followers = followers,
            following = following,
        )
    }
}