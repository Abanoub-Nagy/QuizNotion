package com.example.quiznotion.presentation.dashboard

data class DashboardState(
    val username: String = "",
    val questionsAttempted: Int = 0,
    val correctAnswers: Int = 0,
)
