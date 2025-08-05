package com.github.masato1230.githubsincezero.domain.usecases

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.testing.asSnapshot
import com.github.masato1230.githubsincezero.data.repositories.githubusers.GitHubUsersRepository
import com.github.masato1230.githubsincezero.domain.model.GitHubUser
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
class GetGitHubUsersUseCaseTest {

    private lateinit var gitHubUsersRepository: GitHubUsersRepository
    private lateinit var getGitHubUsersUseCase: GetGitHubUsersUseCase
    private lateinit var pagingSource: FakePagingSource

    @BeforeEach
    fun setUp() {
        gitHubUsersRepository = mockk()
        getGitHubUsersUseCase = GetGitHubUsersUseCase(gitHubUsersRepository)
        pagingSource = FakePagingSource(emptyList())
    }

    @Nested
    inner class WithOneHundredUsers {
        @BeforeEach
        fun setUp() {
            val users = (0 until 100).map {
                GitHubUser.createDummy().copy(
                    id = it,
                    login = "user$it"
                )
            }
            pagingSource = FakePagingSource(users = users)
        }

        @AfterEach
        fun tearDown() {
            unmockkAll()
        }

        @Test
        fun `invoke returns PagingData containing users from repository`() = runTest {
            // Arrange
            val expectedUsers = (0 until 100).map {
                GitHubUser.createDummy().copy(
                    id = it,
                    login = "user$it"
                )
            }
            val pagingSource = FakePagingSource(expectedUsers)

            every { gitHubUsersRepository.getUsersPagingSource(perPage = 100) } returns pagingSource

            // Act
            val users = getGitHubUsersUseCase().asSnapshot()

            // Assert
            assertEquals(100, users.size)
            assertEquals(expectedUsers, users)
        }
    }

    @Nested
    inner class WithTwoHundredUsers {
        @BeforeEach
        fun setUp() {
            val users = (0 until 200).map {
                GitHubUser.createDummy().copy(
                    id = it,
                    login = "user$it"
                )
            }
            pagingSource = FakePagingSource(users = users)
        }

        @Test
        fun `invoke with multiple pages returns PagingData with all users`() = runTest {
            // Arrange
            val firstPageUsers = (0 until 100).map {
                GitHubUser.createDummy().copy(
                    id = it,
                    login = "user$it"
                )
            }
            val secondPageUsers = (100 until 200).map {
                GitHubUser.createDummy().copy(
                    id = it,
                    login = "user$it"
                )
            }
            val allUsers = firstPageUsers + secondPageUsers
            val pagingSource = FakePagingSource(allUsers)

            every { gitHubUsersRepository.getUsersPagingSource(perPage = 100) } returns pagingSource

            // Act
            val users = getGitHubUsersUseCase().asSnapshot {
                scrollTo(100)
            }

            assertEquals(200, users.size)
            assertEquals(allUsers, users)
        }
    }

}

private class FakePagingSource(private val users: List<GitHubUser>) :
    PagingSource<Int, GitHubUser>() {

    override fun getRefreshKey(state: PagingState<Int, GitHubUser>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitHubUser> {
        return try {
            val offset = params.key ?: 0
            val limit = params.loadSize
            val data = users.subList(offset, (offset + limit).coerceAtMost(users.size))

            LoadResult.Page(
                data = data,
                prevKey = if (offset == 0) null else offset - limit,
                nextKey = if (offset + limit >= users.size) null else offset + limit
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}