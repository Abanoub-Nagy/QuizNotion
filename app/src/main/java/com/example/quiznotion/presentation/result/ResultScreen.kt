package com.example.quiznotion.presentation.result

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quiznotion.domain.model.QuizQuestion
import com.example.quiznotion.domain.model.UserAnswer
import com.example.quiznotion.presentation.result.component.QuestionItem
import com.example.quiznotion.presentation.result.component.ScoreCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun ResultScreen(
    state: ResultState,
    event: Flow<ResultEvent>,
    onReportIssueClicked: (String) -> Unit,
    onStartNewQuizClicked: () -> Unit = {},
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        event.collect { event ->
            when (event) {
                is ResultEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f), contentPadding = PaddingValues(10.dp)
        ) {
            item {
                ScoreCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 80.dp, horizontal = 10.dp),
                    scorePercentage = state.scorePercentage,
                    correctAnswers = state.correctAnswers,
                    totalQuestions = state.totalQuestions
                )
            }
            item {
                Text(
                    text = "Quiz Questions",
                    style = MaterialTheme.typography.titleLarge,
                    textDecoration = TextDecoration.Underline
                )
            }

            items(state.quizQuestions.size) { index ->
                val userSelectedAnswer =
                    state.userAnswers.find { it.questionId == state.quizQuestions[index].id }?.selectedAnswer
                QuestionItem(
                    question = state.quizQuestions[index],
                    userSelectedAnswer = userSelectedAnswer,
                    onReportIconClicked = {
                        onReportIssueClicked(
                            state.quizQuestions[index].id
                        )
                    })
            }
        }
        Button(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterHorizontally),
            onClick = onStartNewQuizClicked
        ) {
            Text(
                text = "Start New Quiz"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewResultScreen() {
    val dummyQuestions = List(size = 10) { index ->
        QuizQuestion(
            id = "$index",
            topicCode = 1,
            question = "What is the language for Android Dev?",
            allOptions = listOf("Java", "Python", "Dart", "Kotlin"),
            correctAnswer = "Kotlin",
            explanation = "Some Explanation"
        )
    }
    val dummyAnswers = listOf(
        UserAnswer(questionId = "1", selectedAnswer = "Python"),
        UserAnswer(questionId = "0", selectedAnswer = "Kotlin"),
    )
    ResultScreen(
        state = ResultState(
            scorePercentage = 0,
            totalQuestions = 10,
            correctAnswers = 3,
            quizQuestions = dummyQuestions,
            userAnswers = dummyAnswers
        ),
        onReportIssueClicked = {},
        onStartNewQuizClicked = {},
        event = emptyFlow(),
    )
}