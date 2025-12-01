package com.example.quiznotion.presentation.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiznotion.domain.repository.QuizQuestionRepository
import com.example.quiznotion.domain.util.onFailure
import com.example.quiznotion.domain.util.onSuccess
import com.example.quiznotion.presentation.util.getErrorMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResultViewModel(
    private val questionRepository: QuizQuestionRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ResultState())
    val state = _state.asStateFlow()

    private val _event = Channel<ResultEvent>()
    val event = _event.receiveAsFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            getQuizQuestions()
            getUserAnswers()
            updateResult()
        }
    }

    private suspend fun getQuizQuestions() {
        questionRepository.getQuizQuestions().onSuccess { questions ->
            _state.update { it.copy(quizQuestions = questions) }
        }.onFailure { error ->
            _event.send(ResultEvent.ShowToast(error.getErrorMessage()))
        }
    }

    private suspend fun getUserAnswers() {
        questionRepository.getUserAnswers().onSuccess { answers ->
            _state.update { it.copy(userAnswers = answers) }
        }.onFailure { error ->
            _event.send(ResultEvent.ShowToast(error.getErrorMessage()))
        }
    }

    private fun updateResult() {
        val quizQuestions = state.value.quizQuestions
        val userAnswers = state.value.userAnswers
        val totalQuestions = quizQuestions.size
        val correctAnswersCount = userAnswers.count { answer ->
            val question = quizQuestions.find { it.id == answer.questionId }
            question?.correctAnswer == answer.selectedAnswer
        }
        val scorePercentage = if (totalQuestions > 0) {
            (correctAnswersCount * 100) / totalQuestions
        } else 0

        _state.update {
            it.copy(
                totalQuestions = totalQuestions,
                correctAnswers = correctAnswersCount,
                scorePercentage = scorePercentage
            )
        }
    }
}