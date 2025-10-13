package com.example.quiznotion.presentation.dashboard

import com.example.quiznotion.domain.model.QuizTopic

data class DashboardState(
    val username: String = "",
    val questionsAttempted: Int = 0,
    val correctAnswers: Int = 0,
    val quizTopics: List<QuizTopic> = emptyList(),
    val isTopicsLoading: Boolean = false,
    val error: String? = null
)
