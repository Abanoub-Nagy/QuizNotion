package com.example.quiznotion.presentation.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quiznotion.presentation.dashboard.component.UserStatisticsCard

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier, state: DashboardState
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderSection(
            modifier = Modifier.padding(top = 40.dp, start = 10.dp, end = 10.dp),
            username = state.username,
            questionsAttempted = state.questionsAttempted,
            correctAnswers = state.correctAnswers,
            onEditProfileClicked = { /* TODO: Handle edit profile click */ })
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
    Column(modifier = modifier) {
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
        UserStatisticsCard(
            questionsAttempted = questionsAttempted,
            correctAnswers = correctAnswers,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDashboardScreen() {
    val state = DashboardState(
        username = "John Doe",
        questionsAttempted = 42,
        correctAnswers = 30,
    )
    DashboardScreen(
        state = state
    )

}