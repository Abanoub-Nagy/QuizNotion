package com.example.quiznotion.presentation.quiz.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ExitQuizDialog(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    title: String = "Exit Quiz?",
    confirmButtonText: String = "Exit",
    dismissButtonText: String = "Cancel",  // Changed from "No" - more standard
    onDialogDismiss: () -> Unit,
    onConfirmButtonClick: () -> Unit
) {
    if (isOpen) {
        AlertDialog(
            modifier = modifier,
            title = { Text(text = title) },
            text = {
                Text(
                    text = "Are you sure you want to exit? You won't be able to continue from where you left off.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onDismissRequest = onDialogDismiss,
            confirmButton = {
                TextButton(onClick = onConfirmButtonClick) {
                    Text(
                        text = confirmButtonText,
                        color = MaterialTheme.colorScheme.error  // Red for exit action
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDialogDismiss) {
                    Text(text = dismissButtonText)
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewExitQuizDialog() {
    MaterialTheme {  // ADD THIS - Required for Material3 components
        ExitQuizDialog(isOpen = true, onDialogDismiss = {}, onConfirmButtonClick = {})
    }
}