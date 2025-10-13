package com.example.quiznotion.presentation.quiz.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SubmitQuizDialog(
    modifier: Modifier = Modifier,
    isDialogOpen: Boolean = false,
    title: String = "Submit Quiz",
    onConfirmClicked: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    confirmButtonText: String = "Submit",
    dismissButtonText: String = "Cancel",
) {
    if (isDialogOpen) {
        AlertDialog(
            modifier = modifier,
            title = { Text(text = title) },
            text = {
                Text(text = "Are you sure you want to submit the quiz?")
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
    SubmitQuizDialog(
        isDialogOpen = true,
        onConfirmClicked = {},
        onDismissRequest = {},
    )
}