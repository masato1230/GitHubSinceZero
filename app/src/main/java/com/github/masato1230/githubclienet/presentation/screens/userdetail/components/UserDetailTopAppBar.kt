package com.github.masato1230.githubclienet.presentation.screens.userdetail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.masato1230.githubclienet.R
import com.github.masato1230.githubclienet.domain.model.GitHubUser
import com.github.masato1230.githubclienet.presentation.SharedTransitionKey
import com.github.masato1230.githubclienet.presentation.components.avatars.UserAvatar
import com.github.masato1230.githubclienet.presentation.theme.GitHubClienetTheme

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UserDetailTopAppBar(
    login: String,
    avatarUrl: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    scrollBehavior: TopAppBarScrollBehavior,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    with(sharedTransitionScope) {
        TopAppBar(
            title = {
                UserAvatar(
                    login = login,
                    avatarUrl = avatarUrl,
                    size = 40.dp,
                    modifier = Modifier.sharedElement(
                        sharedContentState = sharedTransitionScope.rememberSharedContentState(
                            key = SharedTransitionKey.UserDetailAvatar(login = login).key,
                        ),
                        animatedVisibilityScope = animatedContentScope,
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = login,
                    modifier = Modifier.sharedElement(
                        sharedContentState = sharedTransitionScope.rememberSharedContentState(
                            key = SharedTransitionKey.UserDetailLogin(login = login).key,
                        ),
                        animatedVisibilityScope = animatedContentScope,
                    ),
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = onClickBack,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(
                            id = R.string.content_description_back_button,
                        ),
                    )
                }
            },
            scrollBehavior = scrollBehavior,
            modifier = modifier,
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewUserDetailTopAppBar() {
    val user = GitHubUser.createDummy()
    GitHubClienetTheme {
        SharedTransitionLayout {
            AnimatedContent(targetState = true) {
                if (it) {
                    UserDetailTopAppBar(
                        login = user.login,
                        avatarUrl = user.avatarUrl,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this@AnimatedContent,
                        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                        onClickBack = {},
                    )
                }
            }
        }
    }
}
