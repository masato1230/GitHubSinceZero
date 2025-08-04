package com.github.masato1230.githubclienet.domain.usecases

import android.util.Log
import com.github.masato1230.githubclienet.data.repositories.githubusers.GitHubUsersRepository
import com.github.masato1230.githubclienet.domain.model.GitHubUserAdditionalInfo
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
     * @return Flow of a pair which contains loaded list and isCompleted boolean
     */
    operator fun invoke(login: String): Flow<Pair<List<GitHubUserAdditionalInfo>, Boolean>> =
        flow {
            coroutineScope {
                // start fetching base
                val baseSectionDeferred = async {
                    val userDetail = gitHubUsersRepository.fetchUserDetail(login = login)
                    return@async GitHubUserAdditionalInfo.Base(userDetail = userDetail)
                }
                // start fetching repos
                val repositoriesDeferred = async {
                    return@async try {
                        val repositories =
                            gitHubUsersRepository.fetchUserRepositories(login = login)
                        GitHubUserAdditionalInfo.Repositories(repositories = Result.success(repositories))
                    } catch (e: Exception) {
                        Log.e("GetGitHubUserDetailUseCase", "repositoriesSectionDeferred: ", e)
                        GitHubUserAdditionalInfo.Repositories(repositories = Result.failure(e))
                    }
                }
                // start fetching events
                val eventsDeferred = async {
                    return@async try {
                        val events = gitHubUsersRepository.fetchUserEvents(login = login)
                        GitHubUserAdditionalInfo.Events(events = Result.success(events))
                    } catch (e: Exception) {
                        Log.e("GetGitHubUserDetailUseCase", "eventsSectionDeferred: ", e)
                        GitHubUserAdditionalInfo.Events(events = Result.failure(e))
                    }
                }

                val sections = mutableListOf<GitHubUserAdditionalInfo>()

                // await base
                val baseSection = baseSectionDeferred.await()
                sections.add(baseSection)
                emit(sections to false)
                // await repos
                val reposSection = repositoriesDeferred.await()
                sections.add(reposSection)
                emit(sections to false)
                // await events
                val eventsSection = eventsDeferred.await()
                sections.add(eventsSection)
                emit(sections to true)
            }
        }.flowOn(Dispatchers.IO)
}