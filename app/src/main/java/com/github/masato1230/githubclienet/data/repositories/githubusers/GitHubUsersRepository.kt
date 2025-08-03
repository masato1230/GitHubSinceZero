package com.github.masato1230.githubclienet.data.repositories.githubusers

import com.github.masato1230.githubclienet.domain.model.GitHubUserDetail

interface GitHubUsersRepository {

    /**
     * Create PagingSource to fetch users from the GitHub API.
     * see [api doc](https://docs.github.com/en/rest/users/users?apiVersion=2022-11-28#list-users)
     */
    fun getUsersPagingSource(
        perPage: Int,
    ): GitHubUsersPagingSource

    /**
     * Fetch users from the GitHub API.
     * see [api doc](https://docs.github.com/en/rest/users/users?apiVersion=2022-11-28#get-a-user)
     */
    suspend fun fetchUserDetail(
        login: String,
    ): GitHubUserDetail

    suspend fun fetchUserEvents(
        login: String,
    ) : String
}