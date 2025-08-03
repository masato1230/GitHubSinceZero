package com.github.masato1230.githubclienet.data.entities

import com.github.masato1230.githubclienet.domain.model.GitHubUserDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubUserDetailEntity(
    val login: String,
    val id: Int,
    @SerialName("avatar_url")
    val avatarUrl: String,
    val name: String?,
    val company: String?,
    val blog: String,
    val location: String?,
    val email: String?,
    val hireable: Boolean?,
    val bio: String?,
    @SerialName("twitter_username")
    val twitterUsername: String?,
    @SerialName("public_repos")
    val publicRepos: Int,
    @SerialName("public_gists")
    val publicGists: Int,
    val followers: Int,
    val following: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    val url: String,
    @SerialName("html_url")
    val htmlUrl: String,
    @SerialName("followers_url")
    val followersUrl: String,
    @SerialName("following_url")
    val followingUrl: String,
    @SerialName("gists_url")
    val gistsUrl: String,
    @SerialName("starred_url")
    val starredUrl: String,
    @SerialName("subscriptions_url")
    val subscriptionsUrl: String,
    @SerialName("organizations_url")
    val organizationsUrl: String,
    @SerialName("repos_url")
    val reposUrl: String,
    @SerialName("events_url")
    val eventsUrl: String,
    @SerialName("received_events_url")
    val receivedEventsUrl: String,
) {

    fun toModel(): GitHubUserDetail {
        return GitHubUserDetail(
            login = login,
            name = name,
            company = company,
            blog = blog.takeIf { it.isNotBlank() },
            location = location,
            email = email,
            hireable = hireable,
            bio = bio,
            twitterUsername = twitterUsername,
            publicRepos = publicRepos,
            publicGists = publicGists,
            followers = followers,
            following = following,
        )
    }
}