package com.example.quiznotion.presentation.issue_report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.quiznotion.presentation.issue_report.component.IssueReportScreenTopBar
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
        QuestionCard(
            modifier = Modifier.fillMaxWidth(),
            question = state.quizQuestions,
            isCardExpanded = state.isQuestionCardExpanded,
            onExpandClicked = {},
        )
    }
}