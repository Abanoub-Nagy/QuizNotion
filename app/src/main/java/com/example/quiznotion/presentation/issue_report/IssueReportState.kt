package com.example.quiznotion.presentation.issue_report

import com.example.quiznotion.domain.model.QuizQuestion

data class IssueReportState(
    val quizQuestions: QuizQuestion? = null,
    val isQuestionCardExpanded: Boolean = false,
    val selectedIssueType: IssueType = IssueType.OTHER,
    val otherIssueText: String = "",
    val additionalComments: String = "",
    val emailForNotification: String = "",
)

enum class IssueType(val displayName: String) {
    INCORRECT_ANSWER(
        "Incorrect Answer"
    ),
    UNCLEAR_QUESTION(
        "Unclear Question"
    ),
    TYPOGRAPHICAL_ERROR(
        "Typographical Error"
    ),
    OTHER(
        "Other"
    ),
}
