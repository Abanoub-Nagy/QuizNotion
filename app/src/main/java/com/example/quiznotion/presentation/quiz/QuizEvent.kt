package com.example.quiznotion.presentation.quiz

sealed interface QuizEvent {
    data class ShowErrorMessage(val message: String) : QuizEvent
}