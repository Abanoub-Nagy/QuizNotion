package com.example.quiznotion.presentation.issue_report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quiznotion.presentation.issue_report.component.IssueReportScreenTopBar
import com.example.quiznotion.presentation.issue_report.component.IssueTypeSection
import com.example.quiznotion.presentation.issue_report.component.QuestionCard

@Composable
fun IssueReportScreen(
    state: IssueReportState,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        IssueReportScreenTopBar(
            title = "Issue Report", onBackButtonClicked = {},
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)
                .verticalScroll(state = rememberScrollState())
        ) {
            QuestionCard(
                modifier = Modifier.fillMaxWidth(),
                question = state.quizQuestions,
                isCardExpanded = state.isQuestionCardExpanded,
                onExpandClicked = {},
            )
            Spacer(modifier = Modifier.height(10.dp))
            IssueTypeSection(
                selectedIssueType = state.selectedIssueType,
                onIssueTypeSelected = {},
                onOtherIssueTypeChanged = {},
                otherIssueType = state.otherIssueText
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                value = state.additionalComments,
                onValueChange = { },
                label = { Text(text = "Additional Comment") },
                supportingText = {
                    Text(text = "Describe the issue in detail (Optional)")
                },
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.emailForNotification,
                onValueChange = { },
                label = { Text(text = "Email for Follow-up") },
                singleLine = true,
                supportingText = {
                    Text(text = "(Optional)")
                },
            )
        }
        Button(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterHorizontally),
            onClick = { /* TODO: Handle retake quiz action */ }) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp), text = "Submit Report"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewIssueReportScreen() {
    IssueReportScreen(
        state = IssueReportState()
    )
}