package com.github.masato1230.githubclienet.data.repositories.githubusers

import com.github.masato1230.githubclienet.data.entities.GitHubEventEntity
import com.github.masato1230.githubclienet.data.entities.GitHubRepositoryEntity
import com.github.masato1230.githubclienet.data.entities.GitHubUserDetailEntity
import com.github.masato1230.githubclienet.domain.model.GitHubEvent
import com.github.masato1230.githubclienet.domain.model.GitHubRepositoryModel
import com.github.masato1230.githubclienet.domain.model.GitHubUserDetail
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
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

    override suspend fun fetchUserRepositories(login: String): List<GitHubRepositoryModel> {
        val response = gitHubHttpClient.get("users/$login/repos") {
            url {
                parameters.append("sort", "updated_at")
            }
        }.body<List<GitHubRepositoryEntity>>()
        return response.map {
            it.toModel()
        }
    }

    override suspend fun fetchUserEvents(login: String): List<GitHubEvent> {
        val response = gitHubHttpClient.get("users/$login/events/public").body<List<GitHubEventEntity>>()
        return response.map {
            it.toModel()
        }
    }
}