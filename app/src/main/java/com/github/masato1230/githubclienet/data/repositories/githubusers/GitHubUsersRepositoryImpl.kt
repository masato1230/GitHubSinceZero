package com.github.masato1230.githubclienet.data.repositories.githubusers

import com.github.masato1230.githubclienet.data.entities.GitHubUserDetailEntity
import com.github.masato1230.githubclienet.domain.model.GitHubUserDetail
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject

internal class GitHubUsersRepositoryImpl @Inject constructor(
    private val gitHubHttpClient: HttpClient,
) : GitHubUsersRepository {

    override fun getUsersPagingSource(
        perPage: Int,
    ): GitHubUsersPagingSource {
        return GitHubUsersPagingSource(perPage = perPage, gitHubHttpClient = gitHubHttpClient)
    }

    override suspend fun fetchUserDetail(login: String): GitHubUserDetail {
        val response = gitHubHttpClient.get("users/$login").body<GitHubUserDetailEntity>()
        return response.toModel()
    }

    override suspend fun fetchUserEvents(login: String): String {
        val response = gitHubHttpClient.get("users/$login/events/public").bodyAsText()
        return response
    }
}