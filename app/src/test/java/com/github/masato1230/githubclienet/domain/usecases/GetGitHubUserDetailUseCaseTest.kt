package com.github.masato1230.githubclienet.domain.usecases

import app.cash.turbine.test
import com.github.masato1230.githubclienet.data.repositories.githubusers.GitHubUsersRepository
import com.github.masato1230.githubclienet.domain.model.GitHubEvent
import com.github.masato1230.githubclienet.domain.model.GitHubRepositoryModel
import com.github.masato1230.githubclienet.domain.model.GitHubUserAdditionalInfo
import com.github.masato1230.githubclienet.domain.model.GitHubUserDetail
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetGitHubUserDetailUseCaseTest {

    private lateinit var gitHubUsersRepository: GitHubUsersRepository
    private lateinit var getGitHubUserDetailUseCase: GetGitHubUserDetailUseCase

    private val testDispatcher = StandardTestDispatcher()

    private val login = "testUser"

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        gitHubUsersRepository = mockk()
        getGitHubUserDetailUseCase = GetGitHubUserDetailUseCase(gitHubUsersRepository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Nested
    inner class WithSuccessfulDataFetching {
        private val userDetail = GitHubUserDetail.createDummy()
        private val repositories = listOf(
            GitHubRepositoryModel.createDummy().copy(id = 1),
            GitHubRepositoryModel.createDummy().copy(id = 2),
        )
        private val events = listOf(
            GitHubEvent.createDummy().copy(id = "push"),
            GitHubEvent.createDummy().copy(id = "fork"),
        )

        @BeforeEach
        fun setUpContext() {
            coEvery { gitHubUsersRepository.fetchUserDetail(login) } coAnswers {
                userDetail
            }
            coEvery { gitHubUsersRepository.fetchUserRepositories(login) } coAnswers {
                repositories
            }
            coEvery { gitHubUsersRepository.fetchUserEvents(login) } coAnswers {
                events
            }
        }

        @Test
        fun `invoke should emit sections sequentially`() = runTest {
            // Act & Assert
            getGitHubUserDetailUseCase(login).test {
                val (firstEmit, _) = awaitItem()
                assertEquals(
                    GitHubUserAdditionalInfo.Base(userDetail = userDetail),
                    firstEmit.first(),
                )

                val (secondEmit, _) = awaitItem()
                assertEquals(
                    GitHubUserAdditionalInfo.Repositories(
                        repositories = Result.success(
                            repositories
                        )
                    ),
                    secondEmit[1],
                )

                val (thirdEmit, isCompleted) = awaitItem()
                assertEquals(
                    GitHubUserAdditionalInfo.Events(events = Result.success(events)),
                    thirdEmit[2],
                )
                assertEquals(true, isCompleted)

                awaitComplete()
            }
        }
    }

    @Nested
    inner class WithFailedRepositoryFetching {
        private val userDetail = GitHubUserDetail.createDummy()
        private val repoException = RuntimeException("Failed to fetch repositories")

        @BeforeEach
        fun setUpContext() {
            coEvery { gitHubUsersRepository.fetchUserDetail(login) } returns userDetail
            coEvery { gitHubUsersRepository.fetchUserRepositories(login) } throws repoException
            coEvery { gitHubUsersRepository.fetchUserEvents(login) } returns emptyList()
        }

        @Test
        fun `invoke should handle repository fetch errors as failure and mark invoke`() = runTest {
            // Act & Assert
            getGitHubUserDetailUseCase(login).test {
                val (firstEmit, _) = awaitItem()
                assertEquals(
                    GitHubUserAdditionalInfo.Base(userDetail = userDetail),
                    firstEmit.first()
                )

                val (secondEmit, _) = awaitItem()
                val reposResult =
                    (secondEmit[1] as GitHubUserAdditionalInfo.Repositories).repositories
                assertEquals(true, reposResult.isFailure)
                assertEquals(repoException.message, reposResult.exceptionOrNull()?.message)

                val (thirdEmit, isCompleted) = awaitItem()
                assertEquals(
                    GitHubUserAdditionalInfo.Events(events = Result.success(emptyList())),
                    thirdEmit[2],
                )
                assertEquals(true, isCompleted)

                awaitComplete()
            }
        }
    }
}