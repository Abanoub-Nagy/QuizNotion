package com.example.quiznotion.presentation.dashboard.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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