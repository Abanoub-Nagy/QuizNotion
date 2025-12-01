package com.example.quiznotion.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiznotion.domain.repository.QuizTopicRepository
import com.example.quiznotion.domain.repository.UserPreferencesRepository
import com.example.quiznotion.domain.util.onFailure
import com.example.quiznotion.domain.util.onSuccess
import com.example.quiznotion.presentation.util.getErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val topicRepository: QuizTopicRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state = combine(
        _state,
        userPreferencesRepository.getQuestionsAttempted(),
        userPreferencesRepository.getCorrectAnswers(),
        userPreferencesRepository.getUsername()
    ) { state, questionsAttempted, correctAnswers, username ->
        state.copy(
            questionsAttempted = questionsAttempted,
            correctAnswers = correctAnswers,
            username = username
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )

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