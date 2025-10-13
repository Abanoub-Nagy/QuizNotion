package com.example.quiznotion.presentation.quiz

/**
 * @author Abanoub Nagy
 */

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.quiznotion.presentation.quiz.component.QuestionItem
import com.example.quiznotion.presentation.quiz.component.QuestionNavRow
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
            onTabSelected = { /* TODO: Handle tab selection */ },
        )
        Spacer(modifier = Modifier.height(20.dp))
        QuestionItem(
            modifier = Modifier
                .padding(15.dp)
                .verticalScroll(rememberScrollState()),
            currentQuestionIndex = state.currentQuestionIndex,
            questions = state.questions,
            answers = state.answers,
            onOptionSelected = { questionId, selectedOption ->

            },
        )
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