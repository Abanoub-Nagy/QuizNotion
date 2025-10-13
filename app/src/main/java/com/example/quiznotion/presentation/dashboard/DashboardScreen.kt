package com.example.quiznotion.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.quiznotion.domain.model.QuizTopic
import com.example.quiznotion.presentation.component.ErrorScreen
import com.example.quiznotion.presentation.dashboard.component.ShimmerEffect
import com.example.quiznotion.presentation.dashboard.component.TopicCard
import com.example.quiznotion.presentation.dashboard.component.UserStatisticsCard

@Composable
fun DashboardScreen(
    state: DashboardState
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderSection(
            modifier = Modifier.fillMaxWidth(),
            username = state.username,
            questionsAttempted = state.questionsAttempted,
            correctAnswers = state.correctAnswers,
            onEditProfileClicked = { /* TODO: Handle edit profile click */ })

        QuizTopicSection(
            modifier = Modifier.fillMaxWidth(),
            quizTopics = state.quizTopics,
            isTopicsLoading = state.isTopicsLoading,
            error = state.error,
            onRefreshClicked = { /* TODO: Handle refresh click */ })
    }
}

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    username: String,
    questionsAttempted: Int,
    correctAnswers: Int,
    onEditProfileClicked: () -> Unit
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(modifier = Modifier.padding(top = 40.dp, start = 10.dp, end = 10.dp)) {
            Text(
                text = "Hello!", style = MaterialTheme.typography.bodyMedium
            )
            Row {
                Text(
                    text = username, style = MaterialTheme.typography.headlineMedium
                )
                IconButton(
                    modifier = Modifier.offset(x = (-10).dp, y = (-20).dp),
                    onClick = onEditProfileClicked
                ) {
                    Icon(
                        modifier = Modifier.size(15.dp),
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Profile"
                    )
                }
            }
        }
        UserStatisticsCard(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .padding(10.dp),
            questionsAttempted = questionsAttempted,
            correctAnswers = correctAnswers,
        )
    }
}

@Composable
fun QuizTopicSection(
    modifier: Modifier = Modifier,
    quizTopics: List<QuizTopic> = emptyList(),
    isTopicsLoading: Boolean = false,
    error: String?,
    onRefreshClicked: () -> Unit = {}
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
                    items(7) {
                        ShimmerEffect(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.small)
                                .fillMaxWidth()
                                .height(120.dp)
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
                            onTopicClicked = { /* TODO: Handle topic click */ })
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
@PreviewScreenSizes
@Composable
private fun PreviewDashboardScreen() {
    val dummyQuizTopic = List(20) {
        QuizTopic(
            id = it.toString(),
            name = "Topic $it",
            imageUrl = "https://picsum.photos/200?random=$it",
            code = it,
        )
    }
    val state = DashboardState(
        username = "John Doe",
        questionsAttempted = 42,
        correctAnswers = 30,
        quizTopics = dummyQuizTopic,
        isTopicsLoading = false,
        error = "Failed to load quiz topics"
    )
    DashboardScreen(
        state = state
    )

}