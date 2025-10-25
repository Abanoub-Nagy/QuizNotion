package com.example.quiznotion.presentation.quiz

sealed interface QuizAction {
    data object PreviousQuestionButtonClicked : QuizAction
    data object NextQuestionButtonClicked : QuizAction
    data class JumpToQuestion(val index: Int) : QuizAction
    data class OnOptionSelected(val questionId: String, val answer: String): QuizAction

}