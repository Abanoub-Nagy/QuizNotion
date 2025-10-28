package com.example.quiznotion.presentation.quiz

/**
 * @author Abanoub Nagy
 */

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun QuizScreen(
    state: QuizState,
    event: Flow<QuizEvent>,
    navigateToDashboardScreen: () -> Unit = {},
    navigateToResultScreen: () -> Unit = {},
    onAction: (QuizAction) -> Unit = {},
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        event.collect { quizEvent ->
            when (quizEvent) {
                is QuizEvent.ShowErrorMessage -> {
                    Toast.makeText(
                        context, quizEvent.message, Toast.LENGTH_LONG
                    ).show()
                }

                QuizEvent.NavigateToDashboardScreen -> {
                    navigateToDashboardScreen()
                }

                QuizEvent.NavigateToResultScreen -> {
                    navigateToResultScreen()
                }
            }
        }
    }

    SubmitQuizDialog(
        isDialogOpen = state.isSubmitDialogOpen,
        onConfirmClicked = {
            onAction(QuizAction.SubmitQuizConfirmButtonClick)
        },
        onDismissRequest = {
            onAction(QuizAction.SubmitQuizDialogDismiss)
        },
    )
    ExitQuizDialog(
        isOpen = state.isExitDialogOpen,
        onDialogDismiss = { onAction(QuizAction.ExitQuizDialogDismiss) },
        onConfirmButtonClick = { onAction(QuizAction.ExitQuizConfirmButtonClick) }
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        QuizScreenTopBar(
            title = state.topBarTitle, onExitClicked = { onAction(QuizAction.ExitQuizButtonClick) },
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
                        onRefreshClicked = {
                            onAction(QuizAction.Refresh)
                        })
                }

                state.questions.isEmpty() -> {
                    ErrorScreen(
                        modifier = Modifier.fillMaxSize(),
                        errorMessage = "No Questions Available",
                        onRefreshClicked = {
                            onAction(QuizAction.Refresh)
                        })
                }

                else -> {
                    QuizScreenContent(
                        state = state,
                        onAction = onAction
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
    onAction: (QuizAction) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        initialPage = state.currentQuestionIndex, pageCount = { state.questions.size })

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }.collect { page ->
            if (page != state.currentQuestionIndex) {
                onAction(QuizAction.JumpToQuestion(page))
            }
        }
    }

    LaunchedEffect(state.currentQuestionIndex) {
        if (pagerState.settledPage != state.currentQuestionIndex) {
            pagerState.animateScrollToPage(state.currentQuestionIndex)
        }
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        QuestionNavRow(
            currentQuestionIndex = state.currentQuestionIndex,
            questions = state.questions,
            answers = state.answers,
            onTabSelected = { questionIndex ->
                onAction(
                    QuizAction.JumpToQuestion(questionIndex)
                )
            },
        )
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalPager(
            state = pagerState, modifier = Modifier.weight(1f)
        ) {
            QuestionItem(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
                    .verticalScroll(rememberScrollState()),
                currentQuestionIndex = state.currentQuestionIndex,
                questions = state.questions,
                answers = state.answers,
                onOptionSelected = { questionId, selectedOption ->
                    onAction(
                        QuizAction.OnOptionSelected(
                            questionId = questionId, answer = selectedOption
                        )
                    )
                },
            )
        }
        QuizSubmitButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            isPreviousEnabled = state.currentQuestionIndex != 0,
            isNextEnabled = state.currentQuestionIndex != state.questions.lastIndex,
            onPreviousClicked = { onAction(QuizAction.PreviousQuestionButtonClicked) },
            onNextClicked = { onAction(QuizAction.NextQuestionButtonClicked) },
            onSubmitClicked = {
                onAction(QuizAction.SubmitQuizButtonClick)
            },
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
        questions = dummyQuestions, answers = dummyAnswers,
//            isLoading = true,
//            loadingErrorText = "Loading... Please wait or check your internet connection..",
        topBarTitle = "Sample Quiz", isSubmitDialogOpen = true
    ),
        navigateToDashboardScreen = {},
        navigateToResultScreen = {},
        onAction = {},
        event = emptyFlow()
    )
}