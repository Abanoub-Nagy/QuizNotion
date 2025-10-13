package com.example.quiznotion.presentation.quiz

import com.example.quiznotion.domain.model.QuizQuestion
import com.example.quiznotion.domain.model.UserAnswer

data class QuizState(
    val questions: List<QuizQuestion> = emptyList(),
    val answers: List<UserAnswer> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val error: String? = null,
    val topBarTitle: String = "",
    val isSubmitDialogOpen: Boolean = false,
    val isExitDialogOpen: Boolean = false,
    val loadingErrorText: String? = null,
    val isLoading: Boolean = false,
)
