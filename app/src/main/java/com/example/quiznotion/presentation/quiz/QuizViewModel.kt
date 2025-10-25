package com.example.quiznotion.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiznotion.data.repository.QuizQuestionRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    private val repository = QuizQuestionRepositoryImpl()
    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()

    init {
        getQuizQuestions()
    }

    fun getQuizQuestions() {
        viewModelScope.launch {
            val quizQuestions = repository.getQuizQuestions()
            _state.value = state.value.copy(
                questions = quizQuestions ?: emptyList()
            )
        }
    }
}