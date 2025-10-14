package com.example.quiznotion.presentation.quiz

/**
 * @author Abanoub Nagy
 */

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quiznotion.domain.model.QuizQuestion
import com.example.quiznotion.domain.model.UserAnswer
import com.example.quiznotion.presentation.component.ErrorScreen
import com.example.quiznotion.presentation.quiz.component.ExitQuizDialog
import com.example.quiznotion.presentation.quiz.component.QuestionItem
import com.example.quiznotion.presentation.quiz.component.QuestionNavRow
import com.example.quiznotion.presentation.quiz.component.QuizScreenLoadingContent
import com.example.quiznotion.presentation.quiz.component.QuizScreenTopBar
import com.example.quiznotion.presentation.quiz.component.QuizSubmitButton
import com.example.quiznotion.presentation.quiz.component.SubmitQuizDialog

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier, state: QuizState
) {
    SubmitQuizDialog(
        isDialogOpen = state.isSubmitDialogOpen,
        onConfirmClicked = { /* TODO: Handle submit action */ },
        onDismissRequest = { /* TODO: Handle dismiss action */ },
    )
    ExitQuizDialog(
        isDialogOpen = state.isExitDialogOpen,
        onConfirmClicked = { /* TODO: Handle submit action */ },
        onDismissRequest = { /* TODO: Handle dismiss action */ },
    )
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        QuizScreenTopBar(
            title = state.topBarTitle, onExitClicked = { /* TODO: Handle exit action */ },
        )
        if (state.isLoading) {
            QuizScreenLoadingContent(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                loadingErrorText = state.loadingErrorText,
            )
        } else {
            when {
                state.error != null -> {
                    ErrorScreen(
                        modifier = Modifier.fillMaxSize(),
                        errorMessage = state.error,
                        onRefreshClicked = {})
                }

                state.questions.isEmpty() -> {
                    ErrorScreen(
                        modifier = Modifier.fillMaxSize(),
                        errorMessage = "No Questions Available",
                        onRefreshClicked = {})
                }

                else -> {
                    QuizScreenContent(
                        state = state
                    )
                }
            }
        }
    }
}

@Composable
fun QuizScreenContent(
    modifier: Modifier = Modifier,
    state: QuizState,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        QuestionNavRow(
            currentQuestionIndex = state.currentQuestionIndex,
            questions = state.questions,
            answers = state.answers,
            onTabSelected = { /* TODO: Handle tab selection */ },
        )
        Spacer(modifier = Modifier.height(20.dp))
        QuestionItem(
            modifier = Modifier
                .weight(1f)
                .padding(15.dp)
                .verticalScroll(rememberScrollState()),
            currentQuestionIndex = state.currentQuestionIndex,
            questions = state.questions,
            answers = state.answers,
            onOptionSelected = { questionId, selectedOption ->

            },
        )
        QuizSubmitButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            isPreviousEnabled = state.currentQuestionIndex != 0,
            isNextEnabled = state.currentQuestionIndex != state.questions.lastIndex,
            onPreviousClicked = {},
            onNextClicked = {},
            onSubmitClicked = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewQuizScreen() {
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
        UserAnswer(questionId = "1", selectedAnswer = ""),
        UserAnswer(questionId = "3", selectedAnswer = ""),
    )
    QuizScreen(
        state = QuizState(
            questions = dummyQuestions,
            answers = dummyAnswers,
//            isLoading = true,
//            loadingErrorText = "Loading... Please wait or check your internet connection..",
            topBarTitle = "Sample Quiz",
            isSubmitDialogOpen = true
        )
    )
}