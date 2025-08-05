package com.github.masato1230.githubsincezero.data.repositories.githubusers

import androidx.paging.PagingSource
import com.github.masato1230.githubsincezero.domain.model.GitHubEvent
import com.github.masato1230.githubsincezero.domain.model.GitHubRepositoryModel
import com.github.masato1230.githubsincezero.domain.model.GitHubUser
import com.github.masato1230.githubsincezero.domain.model.GitHubUserDetail

interface GitHubUsersRepository {

    /**
     * Create PagingSource to fetch users from the GitHub API.
     * see [api doc](https://docs.github.com/en/rest/users/users?apiVersion=2022-11-28#list-users)
     */
    fun getUsersPagingSource(
        perPage: Int,
    ): PagingSource<Int, GitHubUser>

    /**
     * Fetch users from the GitHub API.
     * see [api doc](https://docs.github.com/en/rest/users/users?apiVersion=2022-11-28#get-a-user)
     */
    suspend fun fetchUserDetail(
        login: String,
    ): GitHubUserDetail

    suspend fun fetchUserRepositories(
        login: String,
    ) : List<GitHubRepositoryModel>

    suspend fun fetchUserEvents(
        login: String,
    ) : List<GitHubEvent>
}