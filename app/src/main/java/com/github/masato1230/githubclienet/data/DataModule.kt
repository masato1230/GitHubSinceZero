package com.github.masato1230.githubclienet.data

import com.github.masato1230.githubclienet.GithubClienetConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideGitHubHttpClient(): HttpClient {
        return HttpClient(CIO) {
            defaultRequest {
                url(GithubClienetConstants.GITHUB_BASE_URL)
                headers {
                    append("Accept", "application/vnd.github+json")
                    append("X-GitHub-Api-Version", "2022-11-28")
                }
            }
            install(ContentNegotiation) {
                json()
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 5_000
                socketTimeoutMillis = 20_000
                requestTimeoutMillis = 30_00
            }
        }
    }
}