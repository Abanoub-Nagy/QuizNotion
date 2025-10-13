package com.example.quiznotion.presentation.quiz

/**
 * @author Abanoub Nagy
 */

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quiznotion.domain.model.QuizQuestion
import com.example.quiznotion.domain.model.UserAnswer
import com.example.quiznotion.presentation.quiz.component.QuizScreenTopBar

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier, state: QuizState
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        QuizScreenTopBar(
            title = "Android Quiz", onExitClicked = { /* TODO: Handle exit action */ })
        QuestionNavRow(
            currentQuestionIndex = state.currentQuestionIndex,
            questions = state.questions,
            answers = state.answers,
            onTabSelected = { /* TODO: Handle tab selection */ })
    }
}

@Composable
fun QuestionNavRow(
    modifier: Modifier = Modifier,
    questions: List<QuizQuestion>,
    currentQuestionIndex: Int = 0,
    answers: List<UserAnswer>,
    onTabSelected: (Int) -> Unit = {}
) {
    ScrollableTabRow(
        modifier = modifier, selectedTabIndex = currentQuestionIndex, edgePadding = 0.dp,
    ) {
        questions.forEachIndexed { index, quizQuestion ->
            val containerColor = when {
                answers.any { it.questionId == quizQuestion.id && it.selectedAnswer.isNotEmpty() } -> MaterialTheme.colorScheme.secondaryContainer
                else -> MaterialTheme.colorScheme.surface
            }
            Tab(
                modifier = Modifier.background(containerColor),
                selected = currentQuestionIndex == index,
                onClick = { onTabSelected(index) }) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp), text = "Q${index + 1}"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewQuizScreen() {
    val dummyQuestions = List(10) { index ->
        QuizQuestion(
            id = index.toString(),
            topicCode = 1,
            question = "Sample Question $index",
            allOptions = listOf("Option A", "Option B", "Option C", "Option D"),
            correctAnswer = "Option A",
            explanation = "This is a sample explanation for question $index."
        )
    }
    val dummyAnswers = listOf(
        UserAnswer(questionId = "1", selectedAnswer = "Option A"), // answered
        UserAnswer(questionId = "3", selectedAnswer = ""), // unanswered
    )
    QuizScreen(
        state = QuizState(questions = dummyQuestions, answers = dummyAnswers)
    )
}