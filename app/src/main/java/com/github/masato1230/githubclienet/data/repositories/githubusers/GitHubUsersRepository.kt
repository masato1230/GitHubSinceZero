package com.github.masato1230.githubclienet.data.repositories.githubusers

interface GitHubUsersRepository {

    /**
     * Fetch users from the GitHub API.
     * @see <a href="https://docs.github.com/en/rest/users/users?apiVersion=2022-11-28#list-users />
     */
    suspend fun fetchUsers(
        since: Int,
        perPage: Int = 100,
    ): String
}