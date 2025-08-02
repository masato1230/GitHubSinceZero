package com.github.masato1230.githubclienet.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.github.masato1230.githubclienet.R
import com.github.masato1230.githubclienet.domain.model.GitHubUser
import com.github.masato1230.githubclienet.presentation.components.avatars.CircleInitialAvatar
import com.github.masato1230.githubclienet.presentation.theme.GitHubClienetTheme

@Composable
internal fun HomeGitHubUserListItem(
    user: GitHubUser,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .size(64.dp),
        ) {
            CircleInitialAvatar(
                name = user.name,
            )
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user.avatarUrl)
                        .crossfade(true)
                        .build(),
                ),
                contentDescription = stringResource(
                    id = R.string.content_description_user_avatar_image,
                ),
                modifier = Modifier.fillMaxSize(),
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = user.name,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeGitHubUserListItem() {
    GitHubClienetTheme {
        HomeGitHubUserListItem(
            user = GitHubUser.createDummy(),
        )
    }
}
