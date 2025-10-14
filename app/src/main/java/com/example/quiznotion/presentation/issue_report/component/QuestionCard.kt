package com.example.quiznotion.presentation.issue_report.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quiznotion.domain.model.QuizQuestion

@Composable
fun QuestionCard(
    modifier: Modifier = Modifier,
    question: QuizQuestion?,
    isCardExpanded: Boolean,
    onExpandClicked: () -> Unit = {}
) {
    val transition = updateTransition(targetState = isCardExpanded, label = "cardTransition")
    val iconRotation by transition.animateFloat(label = "iconRotation") { state ->
        if (state) 180f else 0f
    }
    val titleColor by transition.animateColor(label = "titleColor") { state ->
        if (state) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
    }
    val backgroundColor by transition.animateFloat(label = "backgroundColor") { state ->
        if (state) 1f else 0f
    }
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = backgroundColor)
        )
    ) {
        SelectionContainer {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = question?.question.orEmpty(),
                        style = MaterialTheme.typography.titleMedium,
                        color = titleColor
                    )
                    IconButton(
                        onClick = onExpandClicked
                    ) {
                        Icon(
                            modifier = Modifier.rotate(iconRotation),
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Expand Question"
                        )
                    }
                }
                AnimatedVisibility(
                    visible = isCardExpanded
                ) {
                    Column {
                        question?.allOptions?.forEachIndexed { index, option ->
                            val letter = when (index) {
                                0 -> "(A)"
                                1 -> "(B)"
                                2 -> "(C)"
                                3 -> "(D)"
                                else -> ""
                            }
                            Text(
                                text = letter + option, style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = question?.explanation.orEmpty(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewQuestionCard() {
    val sampleQuestion = QuizQuestion(
        id = 1.toString(),
        topicCode = 1,
        question = "What is the language for Android Dev?",
        allOptions = listOf("Java", "Python", "Dart", "Kotlin"),
        correctAnswer = "Kotlin",
        explanation = "Some Explanation"
    )
    QuestionCard(question = sampleQuestion, isCardExpanded = true, onExpandClicked = {})
}