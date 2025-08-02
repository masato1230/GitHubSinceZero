package com.github.masato1230.githubclienet.data.repositories.githubusers

import com.github.masato1230.githubclienet.data.entities.GitHubUserEntity
import com.github.masato1230.githubclienet.domain.model.GitHubUser
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

internal class GitHubUsersRepositoryImpl @Inject constructor(
    private val gitHubHttpClient: HttpClient,
) : GitHubUsersRepository {
    override suspend fun fetchUsers(since: Int, perPage: Int): List<GitHubUser> {
        val response = gitHubHttpClient.get("users") {
            url {
                parameters.append("since", since.toString())
                parameters.append("per_page", perPage.toString())
            }
        }.body<List<GitHubUserEntity>>()
        return response.map { it.toModel() }
    }
}