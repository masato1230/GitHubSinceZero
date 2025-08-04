package com.github.masato1230.githubclienet.presentation.screens.userdetail.listitems

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.masato1230.githubclienet.R
import com.github.masato1230.githubclienet.domain.model.GitHubEvent
import com.github.masato1230.githubclienet.presentation.theme.GitHubClienetTheme

internal fun LazyListScope.UserDetailEventsSection(
    eventsResult: Result<List<GitHubEvent>>,
) {
    item(
        key = "events_title",
    ) {
        UserDetailTitleListItem(
            title = stringResource(id = R.string.user_detail_recent_activities),
        )
    }

    eventsResult.onSuccess { events ->
        items(events) { event ->
            Text(
                text = event.toString(),
            )
        }
    }.onFailure {
        item(
            key = "events_error",
        ) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                UserDetailSectionErrorListItem(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewUserDetailEventsSection() {
    GitHubClienetTheme {
        LazyColumn {
            UserDetailEventsSection(
                eventsResult = Result.success(
                    listOf(
                        GitHubEvent.createDummy(),
                    )
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewUserDetailEventsSectionFailed() {
    GitHubClienetTheme {
        LazyColumn {
            UserDetailEventsSection(eventsResult = Result.failure(Exception()))

        }
    }
}
