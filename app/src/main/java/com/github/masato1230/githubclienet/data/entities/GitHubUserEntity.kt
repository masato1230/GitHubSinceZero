package com.github.masato1230.githubclienet.data.entities

import com.github.masato1230.githubclienet.domain.model.GitHubUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubUserEntity(
    val name: String? = null,
    val email: String? = null,
    val login: String,
    val id: Int,
    @SerialName("node_id")
    val nodeId: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    @SerialName("gravatar_id")
    val gravatarId: String,
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
    val type: UserType,
    @SerialName("site_admin")
    val siteAdmin: Boolean,
    @SerialName("starred_at")
    val starredAt: String? = null,
    @SerialName("user_view_type")
    val userViewType: String? = null,
) {

    @Serializable
    enum class UserType {
        @SerialName("User")
        USER,

        @SerialName("Organization")
        ORGANIZATION,
    }

    fun toModel(): GitHubUser {
        return GitHubUser(
            id = id,
            name = login,
            avatarUrl = avatarUrl,
        )
    }
}