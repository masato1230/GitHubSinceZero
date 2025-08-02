package com.github.masato1230.githubclienet.presentation.components.avatars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import kotlin.math.absoluteValue

/**
 * CircleInitialAvatar composable displays an initial avatar based on the provided name.
 * See ["Building a Circle Initial Avatar in Jetpack Compose"](https://medium.com/@maliksaif070/building-a-circle-initial-avatar-in-jetpack-compose-d0721a89dc00)
 */
@Composable
fun CircleInitialAvatar(
    name: String,
    modifier: Modifier = Modifier,
) {
    val initial = name.trim().firstOrNull()?.uppercase() ?: "?"
    val backgroundColor = getColorFromName(name)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .fillMaxSize()
    ) {
        Text(
            text = initial,
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black
        )
    }
}

private fun getColorFromName(name: String): Color {
    val colors = listOf(
        Color(0xFFB3E5FC), // Light Blue
        Color(0xFFFFF9C4), // Light Yellow
        Color(0xFFC8E6C9), // Light Green
        Color(0xFFFFCCBC), // Light Orange
        Color(0xFFD1C4E9), // Light Purple
        Color(0xFFFFCDD2)  // Light Red
    )

    val index = (name.hashCode().absoluteValue) % colors.size
    return colors[index]
}
