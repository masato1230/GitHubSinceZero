package com.github.masato1230.githubclienet.data.repositories.githubusers

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject

internal class GitHubUsersRepositoryImpl @Inject constructor(
    private val gitHubHttpClient: HttpClient,
) : GitHubUsersRepository {
    override suspend fun fetchUsers(since: Int, perPage: Int) : String {
        val response = gitHubHttpClient.get("users") {
            url {
                parameters.append("since", since.toString())
                parameters.append("per_page", perPage.toString())
            }
        }
        return response.bodyAsText()
    }
}