package com.example.quiznotion.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiznotion.domain.repository.QuizQuestionRepository
import com.example.quiznotion.domain.repository.QuizTopicRepository
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
            val quizTopics = topicRepository.getQuizTopics()
            _state.update {
                it.copy(
                    quizTopics = quizTopics ?: emptyList(),
                )
            }
        }
    }
}