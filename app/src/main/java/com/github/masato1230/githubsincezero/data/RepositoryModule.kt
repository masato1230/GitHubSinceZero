package com.github.masato1230.githubsincezero.data

import com.github.masato1230.githubsincezero.data.repositories.githubusers.GitHubUsersRepository
import com.github.masato1230.githubsincezero.data.repositories.githubusers.GitHubUsersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindGitHubUsersRepository(
        gitHubUsersRepositoryImpl: GitHubUsersRepositoryImpl,
    ) : GitHubUsersRepository
}