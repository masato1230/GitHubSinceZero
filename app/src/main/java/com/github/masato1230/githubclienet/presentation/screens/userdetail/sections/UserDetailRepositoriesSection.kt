package com.github.masato1230.githubclienet.presentation.screens.userdetail.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ForkRight
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.masato1230.githubclienet.R
import com.github.masato1230.githubclienet.domain.model.GitHubRepositoryModel
import com.github.masato1230.githubclienet.presentation.screens.userdetail.components.WithIconRow
import com.github.masato1230.githubclienet.presentation.screens.userdetail.sections.comopnents.UserDetailSectionErrorText
import com.github.masato1230.githubclienet.presentation.screens.userdetail.sections.comopnents.UserDetailSectionTitle
import com.github.masato1230.githubclienet.presentation.theme.GitHubClienetTheme
import com.github.masato1230.githubclienet.presentation.utils.CustomDateFormatters

@Composable
internal fun UserDetailRepositoriesSection(
    repositoriesResult: Result<List<GitHubRepositoryModel>>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        UserDetailSectionTitle(
            title = stringResource(id = R.string.user_detail_repository),
            modifier = Modifier.padding(horizontal = 24.dp),
        )
        repositoriesResult.onSuccess { repositories ->
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    repositories,
                    key = { it.fullName },
                ) { repository ->
                    Card(
                        modifier = Modifier.size(width = 240.dp, height = 300.dp),
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                        ) {
                            Text(
                                text = repository.fullName,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                ),
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            val updatedDateTimeString =
                                repository.updatedAt.format(CustomDateFormatters.defaultDateTimeFormatter)
                            Text(
                                text = "${stringResource(id = R.string.updated_date_time)}: $updatedDateTimeString",
                                style = MaterialTheme.typography.labelSmall,
                            )
                            repository.language?.let {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.labelSmall,
                                )
                            }

                            Box(
                                modifier = Modifier.weight(1f, fill = true),
                            ) {
                                repository.description?.let {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyMedium,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                }
                            }
                            WithIconRow(
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                    )
                                },
                                text = "star: ${repository.stargazersCount}"
                            )
                            WithIconRow(
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.People,
                                        contentDescription = null,
                                    )
                                },
                                text = "watchers: ${repository.watchersCount}"
                            )
                            WithIconRow(
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.ForkRight,
                                        contentDescription = null,
                                    )
                                },
                                text = "forks: ${repository.forksCount}"
                            )
                            WithIconRow(
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.TaskAlt,
                                        contentDescription = null,
                                    )
                                },
                                text = "issues: ${repository.openIssuesCount}"
                            )
                        }
                    }
                }
            }
        }.onFailure {
            Spacer(modifier = Modifier.height(8.dp))
            UserDetailSectionErrorText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }

    }
}

@Preview
@Composable
private fun PreviewUserDetailRepositoriesSection() {
    GitHubClienetTheme {
        UserDetailRepositoriesSection(
            repositoriesResult = Result.success(listOf(GitHubRepositoryModel.createDummy()))
        )
    }
}

@Preview
@Composable
private fun PreviewUserDetailRepositoriesSection_Failure() {
    GitHubClienetTheme {
        UserDetailRepositoriesSection(
            repositoriesResult = Result.failure(Exception())
        )
    }
}