package com.github.masato1230.githubsincezero.presentation.screens.userdetail.listitems

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.masato1230.githubsincezero.domain.model.GitHubUserDetail
import com.github.masato1230.githubsincezero.presentation.screens.userdetail.components.WithIconRow
import com.github.masato1230.githubsincezero.presentation.theme.GitHubSinceZeroTheme

@Composable
internal fun UserDetailBaseListItem(
    userDetail: GitHubUserDetail,
    modifier: Modifier = Modifier,
    onClickXAccount: (String) -> Unit,
    onClickBlogLink: (String) -> Unit,
) {
    val density = LocalDensity.current
    Column(
        modifier = modifier.padding(horizontal = 24.dp)
    ) {
        userDetail.name?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.headlineMedium,
            )
        }
        userDetail.bio?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        userDetail.email?.let {
            WithIconRow(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = null,
                    )
                },
                text = it,
            )
        }
        userDetail.twitterUsername?.let {
            WithIconRow(
                icon = {
                    Text(
                        "X:",
                        fontSize = with(density) {
                            16.dp.toSp()
                        },
                    )
                },
                text = it,
                onClick = {
                    onClickXAccount(it)
                }
            )
        }
        userDetail.company?.let {
            WithIconRow(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Business,
                        contentDescription = null,
                    )
                },
                text = it,
            )
        }
        userDetail.blog?.let {
            WithIconRow(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Link,
                        contentDescription = null,
                    )
                },
                text = it,
                onClick = {
                    onClickBlogLink(it)
                }
            )
        }
        userDetail.location?.let {
            WithIconRow(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                    )
                },
                text = it,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            @Composable
            fun buildStatAnnotatedString(
                key: String,
                value: Int,
                suffix: String,
            ): AnnotatedString = buildAnnotatedString {
                withStyle(
                    style = MaterialTheme.typography.bodyMedium.toSpanStyle()
                ) {
                    append("$key: ")
                    withStyle(
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                        ).toSpanStyle()
                    ) {
                        append(value.toString())
                    }
                    append(suffix)
                }
            }
            Text(
                text = buildStatAnnotatedString(
                    key = "followers",
                    value = userDetail.followers,
                    suffix = ",",
                )
            )
            Text(
                text = buildStatAnnotatedString(
                    key = "following",
                    value = userDetail.following,
                    suffix = ",",
                )
            )
            Text(
                text = buildStatAnnotatedString(
                    key = "repos",
                    value = userDetail.publicRepos,
                    suffix = ",",
                )
            )
            Text(
                text = buildStatAnnotatedString(
                    key = "gists",
                    value = userDetail.publicGists,
                    suffix = "",
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewUserDetailBaseListItem() {
    GitHubSinceZeroTheme {
        val userDetail = GitHubUserDetail.createDummy()
        UserDetailBaseListItem(
            userDetail = userDetail,
            onClickXAccount = {},
            onClickBlogLink = {},
        )
    }
}
