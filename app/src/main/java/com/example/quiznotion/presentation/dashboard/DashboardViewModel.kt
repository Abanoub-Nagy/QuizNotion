package com.example.quiznotion.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiznotion.data.mapper.toQuizTopics
import com.example.quiznotion.data.remote.HttpClientFactory
import com.example.quiznotion.data.remote.KtorRemoteQuizDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()

    private val httpClient = HttpClientFactory.create()
    private val remoteQuizDataSource = KtorRemoteQuizDataSource(httpClient)

    init {
        getQuizTopics()
    }

    private fun getQuizTopics() {
        viewModelScope.launch {
            val quizTopicsDto = remoteQuizDataSource.getQuizTopics()
            _state.update {
                it.copy(
                    quizTopics = quizTopicsDto?.toQuizTopics() ?: emptyList()
                )
            }
        }
    }
}