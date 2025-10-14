package com.example.quiznotion.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object DashBoardScreen : Route

    @Serializable
    data class QuizScreen(val topicCode: Int) : Route

    @Serializable
    data object ResultScreen : Route

    @Serializable
    data class IssueReportScreen(val questionId: String) : Route
}