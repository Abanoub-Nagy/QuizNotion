package com.example.quiznotion.presentation.quiz.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun QuizSubmitButton(
    modifier: Modifier = Modifier,
    isPreviousEnabled: Boolean = false,
    isNextEnabled: Boolean = true,
    onPreviousClicked: () -> Unit = {},
    onNextClicked: () -> Unit = {},
    onSubmitClicked: () -> Unit = {},
) {
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.Center
    ) {
        OutlinedIconButton(
            onClick = onPreviousClicked, enabled = isPreviousEnabled
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Previous Question",
            )
        }
        Button(
            modifier = Modifier.padding(horizontal = 30.dp),
            onClick = onSubmitClicked,
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp), text = "Submit"
            )
        }
        OutlinedIconButton(
            onClick = onNextClicked, enabled = isNextEnabled
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Next Question",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewQuizSubmitButton() {
    QuizSubmitButton(
        modifier = Modifier.fillMaxWidth(),
        isPreviousEnabled = false,
        isNextEnabled = true,
        onPreviousClicked = {},
        onNextClicked = {},
        onSubmitClicked = {},
    )
}