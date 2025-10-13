package com.example.quiznotion.presentation.dashboard.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NameEditDialog(
    modifier: Modifier = Modifier,
    isDialogOpen: Boolean = false,
    textFieldValue: String,
    usernameError: String?,
    title: String = "Edit Your Name",
    onConfirmClicked: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    onTextFieldValueChanged: (String) -> Unit = {},
    confirmButtonText: String = "Save",
    dismissButtonText: String = "Cancel",
) {
    if (isDialogOpen) {
        AlertDialog(
            modifier = modifier,
            title = { Text(text = title) },
            text = {
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = onTextFieldValueChanged,
                    singleLine = true,
                    isError = usernameError != null && textFieldValue.isNotBlank(),
                    supportingText = {
                        Text(
                            text = usernameError.orEmpty(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    })
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
private fun PreviewNameEditDialog() {
    NameEditDialog(
        isDialogOpen = true,
        textFieldValue = "",
        usernameError = null,
        onConfirmClicked = {},
        onDismissRequest = {},
        onTextFieldValueChanged = {})
}