package com.example.quiznotion.presentation.result

import com.example.quiznotion.domain.model.QuizQuestion
import com.example.quiznotion.domain.model.UserAnswer

data class ResultState(
    val scorePercentage: Int = 0,
    val totalQuestions: Int = 0,
    val correctAnswers: Int = 0,
    val quizQuestions: List<QuizQuestion> = emptyList(),
    val userAnswers: List<UserAnswer> = emptyList()
)
