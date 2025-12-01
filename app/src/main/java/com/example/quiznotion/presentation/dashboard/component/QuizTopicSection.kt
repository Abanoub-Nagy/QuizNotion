package com.example.quiznotion.presentation.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.quiznotion.domain.model.QuizTopic
import com.example.quiznotion.presentation.component.ErrorScreen

@Composable
fun QuizTopicSection(
    modifier: Modifier = Modifier,
    quizTopics: List<QuizTopic> = emptyList(),
    isTopicsLoading: Boolean = false,
    error: String?,
    onRefreshClicked: () -> Unit = {},
    onTopicSelected: (Int) -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "What topic do you want to improve today?",
            style = MaterialTheme.typography.titleLarge
        )
        if (error != null) {
            ErrorScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                errorMessage = error,
                onRefreshClicked = onRefreshClicked,
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                contentPadding = PaddingValues(15.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(30.dp),
            ) {
                if (isTopicsLoading) {
                    items(6) {
                        ShimmerEffect(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(MaterialTheme.shapes.small)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        )
                    }
                } else {
                    items(quizTopics) { topic ->
                        TopicCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            topicName = topic.name,
                            imageUrl = topic.imageUrl,
                            onTopicClicked = { onTopicSelected(topic.code) })
                    }
                }
            }
        }
    }
}