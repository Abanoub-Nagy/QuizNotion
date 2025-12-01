package com.example.quiznotion.data.mapper

import com.example.quiznotion.data.remote.dto.IssueReportDto
import com.example.quiznotion.data.util.toFormattedDateTimeString
import com.example.quiznotion.domain.model.IssueReport


fun IssueReport.toIssueReportDto() = IssueReportDto(
    quizQuestionId = questionId,
    issueType = issueType,
    additionalComment = additionalComment,
    userEmail = userEmail,
    timestamp = timestampMillis.toFormattedDateTimeString()
)