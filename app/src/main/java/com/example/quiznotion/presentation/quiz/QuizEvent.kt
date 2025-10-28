package com.example.quiznotion.presentation.quiz

sealed interface QuizEvent {
    data class ShowErrorMessage(val message: String) : QuizEvent
    data object NavigateToResultScreen : QuizEvent
    data object NavigateToDashboardScreen : QuizEvent
}