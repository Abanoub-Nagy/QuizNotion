package com.example.quiznotion.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiznotion.domain.repository.QuizTopicRepository
import com.example.quiznotion.domain.util.onFailure
import com.example.quiznotion.domain.util.onSuccess
import com.example.quiznotion.presentation.util.getErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val topicRepository: QuizTopicRepository
) : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()

    init {
        getQuizTopics()
    }

    private fun getQuizTopics() {
        viewModelScope.launch {
            _state.update { it.copy(isTopicsLoading = true) }
            topicRepository.getQuizTopics().onSuccess { topics ->
                _state.update {
                    it.copy(
                        quizTopics = topics, error = null, isTopicsLoading = false
                    )
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        quizTopics = emptyList(),
                        error = error.getErrorMessage(),
                        isTopicsLoading = false
                    )
                }

            }
        }
    }
}