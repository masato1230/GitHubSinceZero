package com.github.masato1230.githubclienet.domain.usecases

import com.github.masato1230.githubclienet.data.repositories.githubusers.GitHubUsersRepository
import com.github.masato1230.githubclienet.domain.model.GitHubUserSection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGitHubUserDetailUseCase @Inject constructor(
    private val gitHubUsersRepository: GitHubUsersRepository,
) {

    operator fun invoke(login: String): Flow<List<GitHubUserSection>> {
        return flow {
            val sections = mutableListOf<GitHubUserSection>()

            // base
            val userDetail = gitHubUsersRepository.fetchUserDetail(login = login)
            val baseSection = GitHubUserSection.BaseSection(userDetail =  userDetail)
            sections.add(baseSection)

            emit(sections)
        }
    }
}