package com.github.masato1230.githubclienet.domain.usecases

import com.github.masato1230.githubclienet.data.repositories.githubusers.GitHubUsersRepository
import com.github.masato1230.githubclienet.domain.model.GitHubUserSection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetGitHubUserDetailUseCase @Inject constructor(
    private val gitHubUsersRepository: GitHubUsersRepository,
) {

    /**
     * @return Flow of a pair which contains loaded list and isLoading boolean
     */
    operator fun invoke(login: String): Flow<Pair<List<GitHubUserSection>, Boolean>> =
        flow {
            coroutineScope {
                // start fetching base
                val baseSectionDeferred = async {
                    val userDetail = gitHubUsersRepository.fetchUserDetail(login = login)
                    return@async GitHubUserSection.BaseSection(userDetail = userDetail)
                }
                // start fetching events
                val eventsSectionDeferred = async {
                    return@async try {
                        val events = gitHubUsersRepository.fetchUserEvents(login = login)
                        GitHubUserSection.EventsSection(events = Result.success(events))
                    } catch (e: Exception) {
                        GitHubUserSection.EventsSection(events = Result.failure(e))
                    }
                }

                val sections = mutableListOf<GitHubUserSection>()

                // await base
                val baseSection = baseSectionDeferred.await()
                sections.add(baseSection)
                emit(sections to false)
                // await events
                val eventsSection = eventsSectionDeferred.await()
                sections.add(eventsSection)
                emit(sections to true)
            }
        }.flowOn(Dispatchers.IO)
}