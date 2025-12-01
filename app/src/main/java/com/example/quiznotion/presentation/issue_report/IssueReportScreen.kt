package com.example.quiznotion.presentation.issue_report

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quiznotion.presentation.issue_report.component.IssueReportScreenTopBar
import com.example.quiznotion.presentation.issue_report.component.IssueTypeSection
import com.example.quiznotion.presentation.issue_report.component.QuestionCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun IssueReportScreen(
    state: IssueReportState,
    event: Flow<IssueReportEvent>,
    onAction: (IssueReportAction) -> Unit,
    navigateUp: () -> Unit = {},
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        event.collect { event ->
            when (event) {
                is IssueReportEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                IssueReportEvent.NavigateUp -> {
                    navigateUp()
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        IssueReportScreenTopBar(
            title = "Issue Report", onBackButtonClicked = navigateUp,
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
                onExpandClicked = { onAction(IssueReportAction.ExpandQuestionCard) },
            )
            Spacer(modifier = Modifier.height(10.dp))
            IssueTypeSection(
                selectedIssueType = state.selectedIssueType,
                onIssueTypeSelected = { onAction(IssueReportAction.SetIssueReportType(it)) },
                onOtherIssueTypeChanged = { onAction(IssueReportAction.SetOtherIssueText(it)) },
                otherIssueType = state.otherIssueText
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                value = state.additionalComments,
                onValueChange = {
                    onAction(IssueReportAction.SetAdditionalComment(it))
                },
                label = { Text(text = "Additional Comment") },
                supportingText = {
                    Text(text = "Describe the issue in detail (Optional)")
                },
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.emailForNotification,
                onValueChange = {
                    onAction(IssueReportAction.SetEmailForFollowUp(it))
                },
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
                        onClick = {
                            onAction(IssueReportAction.SubmitReport)
                        }) {
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
        state = IssueReportState(),
        navigateUp = {},
        onAction = {},
        event = emptyFlow()
    )
}