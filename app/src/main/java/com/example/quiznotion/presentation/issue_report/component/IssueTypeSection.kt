package com.example.quiznotion.presentation.issue_report.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quiznotion.presentation.issue_report.IssueType

@Composable
fun IssueTypeSection(
    modifier: Modifier = Modifier,
    selectedIssueType: IssueType = IssueType.INCORRECT_ANSWER,
    otherIssueType: String = "",
    onOtherIssueTypeChanged: (String) -> Unit = {},
    onIssueTypeSelected: (IssueType) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Issue Type", style = MaterialTheme.typography.bodySmall
        )
        FlowRow {
            IssueType.entries.forEach { issueType ->
                Row(
                    modifier = Modifier
                        .widthIn(250.dp)
                        .clickable {
                            onIssueTypeSelected(issueType)
                        }, verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = issueType == selectedIssueType,
                        onClick = { onIssueTypeSelected(issueType) },
                    )
                    if (issueType == IssueType.OTHER) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = otherIssueType,
                            onValueChange = onOtherIssueTypeChanged,
                            label = { Text(text = issueType.displayName) },
                            singleLine = true,
                            enabled = selectedIssueType == IssueType.OTHER,
                        )
                    } else {
                        Text(
                            text = issueType.displayName
                        )
                    }
                }
            }
        }
    }
}