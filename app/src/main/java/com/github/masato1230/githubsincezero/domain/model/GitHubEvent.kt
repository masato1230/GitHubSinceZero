package com.github.masato1230.githubsincezero.domain.model

import java.time.ZonedDateTime

data class GitHubEvent(
    val id: String,
    val title: String,
    val text: String,
    val destinationUrl: String,
    val date: ZonedDateTime
) {

    companion object {

        fun createDummy(): GitHubEvent {
            return GitHubEvent(
                id = "id",
                title = "Dummy Event",
                text = "This is a dummy event.",
                destinationUrl = "https://example.com",
                date = ZonedDateTime.now()
            )
        }
    }
}