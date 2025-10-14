package com.example.quiznotion.presentation.issue_report

import com.example.quiznotion.domain.model.QuizQuestion

data class IssueReportState(
    val quizQuestions: QuizQuestion? = null,
    val isQuestionCardExpanded: Boolean = false,
)
