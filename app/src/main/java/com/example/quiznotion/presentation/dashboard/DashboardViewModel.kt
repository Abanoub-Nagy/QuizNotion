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

    fun onAction(action: DashboardAction) {
        when(action) {
            DashboardAction.NameEditIconClick -> {
                _state.update {
                    it.copy(
                        nameTextFieldValue = state.value.username,
                        isNameEditDialogOpen = true
                    )
                }
            }
            DashboardAction.NameEditDialogConfirm -> {
                _state.update { it.copy(isNameEditDialogOpen = false) }
                saveUsername(state.value.nameTextFieldValue)
            }
            DashboardAction.NameEditDialogDismiss -> {
                _state.update { it.copy(isNameEditDialogOpen = false) }
            }

            is DashboardAction.SetUsername -> {
                val usernameError = validateUsername(action.username)
                _state.update {
                    it.copy(
                        nameTextFieldValue = action.username,
                        usernameError = usernameError
                    )
                }
            }

            DashboardAction.RefreshIconClick -> {
                getQuizTopics()
            }
        }
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

    private fun saveUsername(username: String) {
        viewModelScope.launch {
            val trimmedUsername = username.trim()
            userPreferencesRepository.saveUsername(trimmedUsername)
        }
    }

    private fun validateUsername(username: String): String? {
        return when {
            username.isBlank() -> "Please enter your name."
            username.length < 3 -> "Name is too short."
            username.length > 20 -> "Name is too long."
            else -> null
        }
    }
}