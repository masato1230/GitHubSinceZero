package com.github.masato1230.githubclienet.presentation.screens.userdetail.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.masato1230.githubclienet.R
import com.github.masato1230.githubclienet.presentation.theme.GitHubClienetTheme

@Composable
fun UserDetailEventsSection(
    eventsResult: Result<String>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
    ) {
        Text(
            text = stringResource(id = R.string.user_detail_recent_activities),
            style = MaterialTheme.typography.headlineSmall,
        )
        eventsResult.onSuccess { events ->
            Text(
                text = events,
            )
        }.onFailure {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(
                    id = R.string.communication_failed,
                ),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.error,
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewUserDetailEventsSection() {
    GitHubClienetTheme {
        UserDetailEventsSection(eventsResult = Result.success(""))
    }
}

@Preview
@Composable
private fun PreviewUserDetailEventsSectionFailed() {
    GitHubClienetTheme {
        UserDetailEventsSection(eventsResult = Result.failure(Exception()))
    }
}
