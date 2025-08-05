package com.github.masato1230.githubsincezero.data.entities

import com.github.masato1230.githubsincezero.data.serializers.ZonedDateTimeSerializer
import com.github.masato1230.githubsincezero.domain.model.GitHubEvent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlinx.serialization.json.JsonElement
import java.time.ZonedDateTime

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("type")
sealed class GitHubEventEntity {

    abstract val id: String
    abstract val actor: UserEntity
    abstract val repo: RepoEntity
    abstract val createdAt: ZonedDateTime

    @Serializable
    @SerialName("CommitCommentEvent")
    data class CommitCommentEvent(
        override val id: String,
        override val actor: UserEntity,
        override val repo: RepoEntity,
        @SerialName("created_at")
        @Serializable(with = ZonedDateTimeSerializer::class)
        override val createdAt: ZonedDateTime,
        val payload: CommitCommentPayload,
    ) : GitHubEventEntity()

    @Serializable
    @SerialName("CreateEvent")
    data class CreateEvent(
        override val id: String,
        override val actor: UserEntity,
        override val repo: RepoEntity,
        @SerialName("created_at")
        @Serializable(with = ZonedDateTimeSerializer::class)
        override val createdAt: ZonedDateTime,
        val payload: CreatePayload,
    ) : GitHubEventEntity()

    @Serializable
    @SerialName("DeleteEvent")
    data class DeleteEvent(
        override val id: String,
        override val actor: UserEntity,
        override val repo: RepoEntity,
        @SerialName("created_at")
        @Serializable(with = ZonedDateTimeSerializer::class)
        override val createdAt: ZonedDateTime,
        val payload: DeletePayload,
    ) : GitHubEventEntity()

    @Serializable
    @SerialName("ForkEvent")
    data class ForkEvent(
        override val id: String,
        override val actor: UserEntity,
        override val repo: RepoEntity,
        @SerialName("created_at")
        @Serializable(with = ZonedDateTimeSerializer::class)
        override val createdAt: ZonedDateTime,
        val payload: ForkPayload,
    ) : GitHubEventEntity()

    @Serializable
    @SerialName("IssueCommentEvent")
    data class IssueCommentEvent(
        override val id: String,
        override val actor: UserEntity,
        override val repo: RepoEntity,
        @SerialName("created_at")
        @Serializable(with = ZonedDateTimeSerializer::class)
        override val createdAt: ZonedDateTime,
        val payload: IssueCommentPayload,
    ) : GitHubEventEntity()

    @Serializable
    @SerialName("IssuesEvent")
    data class IssuesEvent(
        override val id: String,
        override val actor: UserEntity,
        override val repo: RepoEntity,
        @SerialName("created_at")
        @Serializable(with = ZonedDateTimeSerializer::class)
        override val createdAt: ZonedDateTime,
        val payload: IssuesPayload,
    ) : GitHubEventEntity()

    @Serializable
    @SerialName("PullRequestEvent")
    data class PullRequestEvent(
        override val id: String,
        override val actor: UserEntity,
        override val repo: RepoEntity,
        @SerialName("created_at")
        @Serializable(with = ZonedDateTimeSerializer::class)
        override val createdAt: ZonedDateTime,
        val payload: PullRequestPayload,
    ) : GitHubEventEntity()

    @Serializable
    @SerialName("PullRequestReviewEvent")
    data class PullRequestReviewEvent(
        override val id: String,
        override val actor: UserEntity,
        override val repo: RepoEntity,
        @SerialName("created_at")
        @Serializable(with = ZonedDateTimeSerializer::class)
        override val createdAt: ZonedDateTime,
        val payload: PullRequestReviewPayload,
    ) : GitHubEventEntity()

    @Serializable
    @SerialName("PullRequestReviewCommentEvent")
    data class PullRequestReviewCommentEvent(
        override val id: String,
        override val actor: UserEntity,
        override val repo: RepoEntity,
        @SerialName("created_at")
        @Serializable(with = ZonedDateTimeSerializer::class)
        override val createdAt: ZonedDateTime,
        val payload: PullRequestReviewCommentPayload,
    ) : GitHubEventEntity()

    @Serializable
    @SerialName("PushEvent")
    data class PushEvent(
        override val id: String,
        override val actor: UserEntity,
        override val repo: RepoEntity,
        @SerialName("created_at")
        @Serializable(with = ZonedDateTimeSerializer::class)
        override val createdAt: ZonedDateTime,
        val payload: PushPayload,
    ) : GitHubEventEntity()

    @Serializable
    @SerialName("ReleaseEvent")
    data class ReleaseEvent(
        override val id: String,
        override val actor: UserEntity,
        override val repo: RepoEntity,
        @SerialName("created_at")
        @Serializable(with = ZonedDateTimeSerializer::class)
        override val createdAt: ZonedDateTime,
        val payload: ReleasePayload,
    ) : GitHubEventEntity()

    @Serializable
    @SerialName("WatchEvent")
    data class WatchEvent(
        override val id: String,
        override val actor: UserEntity,
        override val repo: RepoEntity,
        @SerialName("created_at")
        @Serializable(with = ZonedDateTimeSerializer::class)
        override val createdAt: ZonedDateTime,
        val payload: WatchPayload,
    ) : GitHubEventEntity()

    @Serializable
    @SerialName("OtherEvent")
    data class OtherEvent(
        override val id: String,
        override val actor: UserEntity,
        override val repo: RepoEntity,
        @SerialName("created_at")
        @Serializable(with = ZonedDateTimeSerializer::class)
        override val createdAt: ZonedDateTime,
        val payload: JsonElement,
    ) : GitHubEventEntity()

    // Payloads
    @Serializable
    data class CommitCommentPayload(
        val action: String,
        val comment: CommitCommentEntity,
    )

    @Serializable
    data class CreatePayload(
        val ref: String?,
        @SerialName("ref_type") val refType: String,
        @SerialName("master_branch") val masterBranch: String?,
        val description: String?,
        @SerialName("pusher_type") val pusherType: String,
    )

    @Serializable
    data class DeletePayload(
        val ref: String,
        @SerialName("ref_type") val refType: String,
        @SerialName("pusher_type") val pusherType: String,
    )

    @Serializable
    data class ForkPayload(
        val forkee: ForkeeEntity,
    )

    @Serializable
    data class IssueCommentPayload(
        val action: String,
        val issue: IssueEntity,
        val comment: CommentEntity,
    )

    @Serializable
    data class IssuesPayload(
        val action: String,
        val issue: IssueEntity,
    )

    @Serializable
    data class PullRequestPayload(
        val action: String,
        val number: Int,
        @SerialName("pull_request") val pullRequest: PullRequestEntity,
    )

    @Serializable
    data class PullRequestReviewPayload(
        val action: String,
        val review: PullRequestReviewEntity,
        @SerialName("pull_request") val pullRequest: PullRequestEntity,
    )

    @Serializable
    data class PullRequestReviewCommentPayload(
        val action: String,
        val comment: CommentEntity,
        @SerialName("pull_request") val pullRequest: PullRequestEntity,
    )

    @Serializable
    data class PushPayload(
        @SerialName("push_id") val pushId: Long,
        val size: Int,
        val ref: String,
        val head: String,
        val before: String,
        val commits: List<CommitEntity>,
    )

    @Serializable
    data class ReleasePayload(
        val action: String,
        val release: ReleaseEntity,
    )

    @Serializable
    data class WatchPayload(
        val action: String,
    )

    @Serializable
    data class RepoEntity(
        val id: Int,
        val name: String,
        @SerialName("url") val url: String,
    )

    @Serializable
    data class UserEntity(
        val id: Int,
        val login: String,
        @SerialName("avatar_url") val avatarUrl: String,
    )

    @Serializable
    data class CommitCommentEntity(
        val id: String,
        val user: UserEntity,
        val body: String,
        @SerialName("created_at")
        @Serializable(with = ZonedDateTimeSerializer::class)
        val createdAt: ZonedDateTime,
        @SerialName("updated_at")
        @Serializable(with = ZonedDateTimeSerializer::class)
        val updatedAt: ZonedDateTime,
    )

    @Serializable
    data class ForkeeEntity(
        val name: String,
        @SerialName("full_name") val fullName: String,
        val owner: UserEntity,
        val description: String?,
    )

    @Serializable
    data class CommentEntity(
        val user: UserEntity,
        val body: String,
        @SerialName("created_at")
        @Serializable(with = ZonedDateTimeSerializer::class)
        val createdAt: ZonedDateTime,
        @SerialName("updated_at")
        @Serializable(with = ZonedDateTimeSerializer::class)
        val updatedAt: ZonedDateTime,
    )

    @Serializable
    data class IssueEntity(
        val number: Int,
        val title: String,
        val user: UserEntity,
    )

    @Serializable
    data class PullRequestEntity(
        val number: Int,
        val title: String,
        val user: UserEntity,
    )

    @Serializable
    data class PullRequestReviewEntity(
        val user: UserEntity,
        val body: String?,
    )

    @Serializable
    data class CommitEntity(
        val sha: String,
        val author: CommitAuthorEntity,
        val message: String,
    ) {
        @Serializable
        data class CommitAuthorEntity(
            val name: String,
            val email: String,
        )
    }

    @Serializable
    data class ReleaseEntity(
        @SerialName("tag_name") val tagName: String,
        val name: String?,
        val author: UserEntity,
    )

    // FIXME: Stop hardcoding japanese
    fun toModel(): GitHubEvent? {
        val repoName = this.repo.name
        val createdAt = this.createdAt

        return when (this) {
            is CommitCommentEvent -> GitHubEvent(
                id = id,
                title = "$repoName のコミットにコメント",
                text = payload.comment.body,
                destinationUrl = "https://github.com/${repoName}/commit/${payload.comment.id}",
                date = createdAt
            )

            is CreateEvent -> GitHubEvent(
                id = id,
                title = when (payload.refType) {
                    "repository" -> "リポジトリ `$repoName` を作成"
                    "branch" -> "`$repoName` にブランチ `${payload.ref}` を作成"
                    "tag" -> "`$repoName` にタグ `${payload.ref}` を作成"
                    else -> "`$repoName` で新規作成"
                },
                text = payload.description ?: "",
                destinationUrl = when (payload.refType) {
                    "branch" -> "https://github.com/${repoName}/tree/${payload.ref}"
                    "tag" -> "https://github.com/${repoName}/releases/tag/${payload.ref}"
                    else -> "https://github.com/${repoName}"
                },
                date = createdAt
            )

            is DeleteEvent -> GitHubEvent(
                id = id,
                title = "`$repoName` から `${payload.ref}` を削除",
                text = "${payload.refType}が削除されました",
                destinationUrl = "https://github.com/${repoName}",
                date = createdAt
            )

            is ForkEvent -> GitHubEvent(
                id = id,
                title = "`$repoName` をフォーク",
                text = "フォーク先リポジトリ: `${payload.forkee.fullName}`",
                destinationUrl = "https://github.com/${payload.forkee.fullName}",
                date = createdAt
            )

            is IssueCommentEvent -> GitHubEvent(
                id = id,
                title = "`$repoName` のIssue #${payload.issue.number} にコメント",
                text = payload.comment.body,
                destinationUrl = "https://github.com/${repoName}/issues/${payload.issue.number}",
                date = createdAt
            )

            is IssuesEvent -> GitHubEvent(
                id = id,
                title = "`$repoName` のIssue #${payload.issue.number} を${
                    when (payload.action) {
                        "opened" -> "オープン"
                        "closed" -> "クローズ"
                        "reopened" -> "再度オープン"
                        else -> "更新"
                    }
                }",
                text = "タイトル: `${payload.issue.title}`",
                destinationUrl = "https://github.com/${repoName}/issues/${payload.issue.number}",
                date = createdAt
            )

            is PullRequestEvent -> GitHubEvent(
                id = id,
                title = "`$repoName` のPR #${payload.pullRequest.number} を${
                    when (payload.action) {
                        "opened" -> "オープン"
                        "closed" -> "クローズ"
                        "reopened" -> "再度オープン"
                        else -> "更新"
                    }
                }",
                text = "タイトル: `${payload.pullRequest.title}`",
                destinationUrl = "https://github.com/${repoName}/pull/${payload.pullRequest.number}",
                date = createdAt
            )

            is PullRequestReviewEvent -> GitHubEvent(
                id = id,
                title = "`$repoName` のPR #${payload.pullRequest.number} にレビュー",
                text = payload.review.body ?: "レビューコメントなし",
                destinationUrl = "https://github.com/${repoName}/pull/${payload.pullRequest.number}",
                date = createdAt
            )

            is PullRequestReviewCommentEvent -> GitHubEvent(
                id = id,
                title = "`$repoName` のPR #${payload.pullRequest.number} にコメント",
                text = payload.comment.body,
                destinationUrl = "https://github.com/${repoName}/pull/${payload.pullRequest.number}",
                date = createdAt
            )

            is PushEvent -> {
                val commitCount = payload.commits.size
                val branchName = payload.ref.removePrefix("refs/heads/")
                GitHubEvent(
                    id = id,
                    title = "`$repoName` のブランチ`${branchName}`にプッシュ",
                    text = "${commitCount}個のコミットをプッシュしました。\n最新メッセージ:\n`${payload.commits.firstOrNull()?.message ?: ""}`",
                    destinationUrl = "https://github.com/${repoName}/tree/${branchName}",
                    date = createdAt
                )
            }

            is ReleaseEvent -> GitHubEvent(
                id = id,
                title = "`$repoName` のリリース `${payload.release.tagName}` を${
                    when (payload.action) {
                        "published" -> "公開"
                        else -> "更新"
                    }
                }",
                text = payload.release.name ?: "タイトルなし",
                destinationUrl = "https://github.com/${repoName}/releases/tag/${payload.release.tagName}",
                date = createdAt
            )

            is WatchEvent -> GitHubEvent(
                id = id,
                title = "`$repoName` にスター",
                text = "お気に入りのリポジトリとして登録されました",
                destinationUrl = "https://github.com/${repoName}",
                date = createdAt
            )

            is OtherEvent -> null
        }
    }
}