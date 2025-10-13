package com.example.quiznotion.presentation.quiz.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quiznotion.domain.model.QuizQuestion
import com.example.quiznotion.domain.model.UserAnswer

@Composable
fun QuestionItem(
    modifier: Modifier = Modifier,
    currentQuestionIndex: Int,
    questions: List<QuizQuestion>,
    answers: List<UserAnswer>,
    onOptionSelected: (String,String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        val currentQuestion = questions[currentQuestionIndex]
        val selectedAnswer = answers.find { it.questionId == currentQuestion.id }?.selectedAnswer
        Text(
            text = currentQuestion.question, style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(10.dp))
        currentQuestion.allOptions.forEach { option ->
            OptionItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                optionText = option,
                isSelected = option == selectedAnswer,
                onOptionSelected = { onOptionSelected(currentQuestion.id, option) })
        }
    }
}