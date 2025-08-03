package com.github.masato1230.githubclienet.data.repositories.githubusers

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.masato1230.githubclienet.data.entities.GitHubUserEntity
import com.github.masato1230.githubclienet.domain.model.GitHubUser
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class GitHubUsersPagingSource(
    private val perPage: Int = 50,
    private val gitHubHttpClient: HttpClient,
) : PagingSource<Int, GitHubUser>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitHubUser> {
        try {
            val nextUserId = params.key ?: 0
            val response = gitHubHttpClient.get("users") {
                url {
                    parameters.append("since", nextUserId.toString())
                    parameters.append("per_page", perPage.toString())
                }
            }.body<List<GitHubUserEntity>>()
            return LoadResult.Page(
                data = response.map { it.toModel() },
                prevKey = null,
                nextKey = response.lastOrNull()?.id
            )
        } catch (e: Exception) {
            return LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GitHubUser>) = state.anchorPosition
}