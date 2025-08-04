package com.github.masato1230.githubclienet.domain.model

import java.time.ZonedDateTime

data class GitHubEvent(
    val title: String,
    val text: String,
    val destinationUrl: String,
    val date: ZonedDateTime
)