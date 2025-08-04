package com.github.masato1230.githubclienet.data

import com.github.masato1230.githubclienet.BuildConfig
import com.github.masato1230.githubclienet.GithubClienetConstants
import com.github.masato1230.githubclienet.data.entities.GitHubEventEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideGitHubHttpClient(): HttpClient {
        return HttpClient(CIO) {
            defaultRequest {
                url(GithubClienetConstants.GITHUB_BASE_URL)
                header("Accept", "application/vnd.github+json")
                header("X-GitHub-Api-Version", "2022-11-28")
                if (BuildConfig.GITHUB_TOKEN.isNotBlank()) {
                    header("Authorization", "Bearer ${BuildConfig.GITHUB_TOKEN}")
                }
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        serializersModule = SerializersModule {
                            polymorphic(GitHubEventEntity::class) {
                                defaultDeserializer {
                                    GitHubEventEntity.OtherEvent.serializer()
                                }
                            }
                        }
                    },
                )
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 10_000
                socketTimeoutMillis = 20_000
                requestTimeoutMillis = 30_00
            }
        }
    }
}