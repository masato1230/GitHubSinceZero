package com.github.masato1230.githubclienet.presentation.screens.userdetail.listitems

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.masato1230.githubclienet.domain.model.GitHubEvent
import com.github.masato1230.githubclienet.presentation.theme.GitHubClienetTheme
import com.github.masato1230.githubclienet.presentation.utils.CustomDateFormatters

@Composable
fun UserDetailEventListItem(
    event: GitHubEvent,
    isLastEvent: Boolean,
    onClick: (GitHubEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onClick(event) },
            border = BorderStroke(
                width = 1.dp,
                color = Color.Gray,
            ),
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            ) {
                Text(
                    text = CustomDateFormatters.defaultDateTimeFormatter.format(event.date),
                    style = MaterialTheme.typography.labelSmall,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.labelLarge,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = event.text,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        if (!isLastEvent) {
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(width = 4.dp, height = 32.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewUserDetailEventListItem() {
    GitHubClienetTheme {
        UserDetailEventListItem(
            event = GitHubEvent.createDummy(),
            isLastEvent = false,
            onClick = {},
        )
    }
}
