package com.github.masato1230.githubclienet.presentation.screens.home.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.masato1230.githubclienet.domain.model.GitHubUser
import com.github.masato1230.githubclienet.presentation.SharedTransitionKey
import com.github.masato1230.githubclienet.presentation.components.avatars.UserAvatar
import com.github.masato1230.githubclienet.presentation.theme.GitHubClienetTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun HomeGitHubUserListItem(
    user: GitHubUser,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    with(sharedTransitionScope) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            UserAvatar(
                login = user.login,
                avatarUrl = user.avatarUrl,
                size = 64.dp,
                modifier = Modifier.sharedElement(
                    sharedContentState = sharedTransitionScope.rememberSharedContentState(
                        key = SharedTransitionKey.UserDetailAvatar(login = user.login).key
                    ),
                    animatedVisibilityScope = animatedContentScope,
                ),
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = user.login,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.sharedElement(
                    sharedContentState = sharedTransitionScope.rememberSharedContentState(
                        key = SharedTransitionKey.UserDetailLogin(login = user.login).key,
                    ),
                    animatedVisibilityScope = animatedContentScope,
                ),
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
private fun PreviewHomeGitHubUserListItem() {
    GitHubClienetTheme {
        SharedTransitionLayout {
            AnimatedContent(targetState = true) {
                if (it) {
                    HomeGitHubUserListItem(
                        user = GitHubUser.createDummy(),
                        animatedContentScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        onClick = {},
                    )
                }
            }
        }
    }
}
