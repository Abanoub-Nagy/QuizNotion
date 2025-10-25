package com.example.quiznotion.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiznotion.data.repository.QuizTopicRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()

    val quizTopicRepository = QuizTopicRepositoryImpl()

    init {
        getQuizTopics()
    }

    private fun getQuizTopics() {
        viewModelScope.launch {
            val quizTopics = quizTopicRepository.getQuizTopics()
            _state.update {
                it.copy(
                    quizTopics = quizTopics ?: emptyList(),
                )
            }
        }
    }
}