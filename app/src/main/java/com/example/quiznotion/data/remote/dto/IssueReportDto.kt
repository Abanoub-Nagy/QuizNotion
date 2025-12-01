package com.example.quiznotion.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class IssueReportDto(
    val quizQuestionId: String,
    val issueType: String,
    val additionalComment: String?,
    val userEmail: String?,
    val timestamp: String
)