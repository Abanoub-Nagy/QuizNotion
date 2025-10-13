package com.example.quiznotion.presentation.result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quiznotion.presentation.result.component.ScoreCard

@Composable
fun ResultScreen(
    state: ResultState
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ScoreCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 80.dp, horizontal = 10.dp),
            scorePercentage = state.scorePercentage,
            correctAnswers = state.correctAnswers,
            totalQuestions = state.totalQuestions
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewResultScreen() {
    ResultScreen(
        state = ResultState(
            scorePercentage = 0, totalQuestions = 10, correctAnswers = 3
        )
    )
}