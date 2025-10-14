package com.example.quiznotion.presentation.issue_report.component

/**
 * @author Abanoub Nagy
 */

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueReportScreenTopBar(
    modifier: Modifier = Modifier, title: String, onBackButtonClicked: () -> Unit = {}
) {
    TopAppBar(modifier = modifier, title = { Text(title) }, navigationIcon = {
        IconButton(
            onClick = onBackButtonClicked
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Navigate Back"
            )
        }
    })
}

@Preview
@Composable
private fun PreviewIssueReportScreenTopBar() {
    IssueReportScreenTopBar(
        title = "Issue Report", onBackButtonClicked = {})
}