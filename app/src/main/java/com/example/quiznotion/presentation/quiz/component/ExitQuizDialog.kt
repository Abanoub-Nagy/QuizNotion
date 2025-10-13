package com.example.quiznotion.presentation.quiz.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ExitQuizDialog(
    modifier: Modifier = Modifier,
    isDialogOpen: Boolean = false,
    title: String = "Exit Quiz?",
    onConfirmClicked: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    confirmButtonText: String = "Exit",
    dismissButtonText: String = "No",
) {
    if (isDialogOpen) {
        AlertDialog(
            modifier = modifier,
            title = { Text(text = title) },
            text = {
                Text(text = "Are you sure you want to exit the quiz? Your progress will not be saved.")
            },
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = onConfirmClicked) {
                    Text(text = confirmButtonText)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = dismissButtonText)
                }
            },
        )
    }

}

@Preview
@Composable
private fun PreviewSubmitQuizDialog() {
    ExitQuizDialog(
        isDialogOpen = true,
        onConfirmClicked = {},
        onDismissRequest = {},
    )
}