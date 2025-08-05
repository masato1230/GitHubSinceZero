package com.github.masato1230.githubsincezero.presentation.components.avatars

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.github.masato1230.githubsincezero.R
import com.github.masato1230.githubsincezero.domain.model.GitHubUser
import com.github.masato1230.githubsincezero.presentation.theme.GitHubSinceZeroTheme

@Composable
fun UserAvatar(
    login: String,
    avatarUrl: String,
    size: Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(size)
    ) {
        CircleInitialAvatar(
            name = login,
        )
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(avatarUrl)
                    .crossfade(true)
                    .build(),
            ),
            contentDescription = stringResource(
                id = R.string.content_description_user_avatar_image,
            ),
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview
@Composable
private fun PreviewUserAvatar() {
    GitHubSinceZeroTheme {
        val user = GitHubUser.createDummy()
        UserAvatar(
            login = user.login,
            avatarUrl = user.avatarUrl,
            size = 64.dp,
        )
    }
}
