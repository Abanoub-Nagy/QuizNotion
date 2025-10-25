package com.example.quiznotion.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiznotion.data.repository.QuizQuestionRepositoryImpl
import com.example.quiznotion.domain.model.UserAnswer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    private val repository = QuizQuestionRepositoryImpl()
    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()

    init {
        getQuizQuestions()
    }

    fun onAction(action: QuizAction) {
        when (action) {
            QuizAction.NextQuestionButtonClicked -> {
                val newIndex =
                    (state.value.currentQuestionIndex + 1).coerceAtMost(state.value.questions.lastIndex)
                _state.update { it.copy(currentQuestionIndex = newIndex) }
            }

            QuizAction.PreviousQuestionButtonClicked -> {
                val newIndex = (state.value.currentQuestionIndex - 1).coerceAtLeast(0)
                _state.update { it.copy(currentQuestionIndex = newIndex) }
            }

            is QuizAction.JumpToQuestion -> {
                _state.update { it.copy(currentQuestionIndex = action.index) }
            }

            is QuizAction.OnOptionSelected -> {
                val currentAnswers = state.value.answers.toMutableList()
                val existingAnswerIndex =
                    currentAnswers.indexOfFirst { it.questionId == action.questionId }
                if (existingAnswerIndex != -1) {
                    currentAnswers[existingAnswerIndex] =
                        UserAnswer(action.questionId, action.answer)
                } else {
                    currentAnswers.add(UserAnswer(action.questionId, action.answer))
                }
                _state.update { it.copy(answers = currentAnswers) }
            }
        }
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