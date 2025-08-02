package com.github.masato1230.githubclienet.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.masato1230.githubclienet.data.repositories.githubusers.GitHubUsersRepository
import com.github.masato1230.githubclienet.domain.model.GitHubUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGitHubUsersUseCase @Inject constructor(
    private val gitHubUsersRepository: GitHubUsersRepository,
) {
    companion object {
        private const val PER_PAGE = 100
    }

    operator fun invoke(): Flow<PagingData<GitHubUser>> {
        return Pager(
            config = PagingConfig(pageSize = PER_PAGE),
            pagingSourceFactory = {
                gitHubUsersRepository.getUsersPagingSource(
                    perPage = PER_PAGE,
                )
            },
        ).flow
    }
}