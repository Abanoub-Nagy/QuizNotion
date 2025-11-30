package com.example.quiznotion.presentation.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.quiznotion.domain.model.UserAnswer
import com.example.quiznotion.domain.repository.QuizQuestionRepository
import com.example.quiznotion.domain.repository.QuizTopicRepository
import com.example.quiznotion.domain.util.onFailure
import com.example.quiznotion.domain.util.onSuccess
import com.example.quiznotion.presentation.navigation.Route
import com.example.quiznotion.presentation.util.getErrorMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel(
    savedStateHandle: SavedStateHandle,
    private val topicRepository: QuizTopicRepository,
    private val questionRepository: QuizQuestionRepository,
) : ViewModel() {
    private val topicCode = savedStateHandle.toRoute<Route.QuizScreen>().topicCode
    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()

    private val _event = Channel<QuizEvent>()
    val event = _event.receiveAsFlow()

    init {
        setupQuiz()
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

            QuizAction.ExitQuizButtonClick -> {
                _state.update { it.copy(isExitDialogOpen = true) }
            }

            QuizAction.ExitQuizDialogDismiss -> {
                _state.update { it.copy(isExitDialogOpen = false) }
            }

            QuizAction.ExitQuizConfirmButtonClick -> {
                _state.update { it.copy(isExitDialogOpen = false) }
                _event.trySend(QuizEvent.NavigateToDashboardScreen)
            }

            QuizAction.SubmitQuizButtonClick -> {
                _state.update { it.copy(isSubmitDialogOpen = true) }
            }

            QuizAction.SubmitQuizDialogDismiss -> {
                _state.update { it.copy(isSubmitDialogOpen = false) }
            }

            QuizAction.SubmitQuizConfirmButtonClick -> {
                _state.update { it.copy(isSubmitDialogOpen = false) }
                viewModelScope.launch {
                    saveUserAnswers()
                }

            }

            QuizAction.Refresh -> {
                setupQuiz()
            }
        }
    }

    private fun setupQuiz() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true, loadingErrorText = "Setting up the quiz..."
                )
            }
            getQuizTopicName(topicCode)
            getQuizQuestions(topicCode)
            _state.update {
                it.copy(
                    isLoading = false,
                    loadingErrorText = null,

                    )
            }
        }
    }

    private suspend fun getQuizQuestions(topicCode: Int) {
        questionRepository.fetchAndSaveQuizQuestions(
            topicCode
        ).onSuccess { questions ->
            _state.update {
                it.copy(
                    questions = questions,
                    error = null,
                )
            }
        }.onFailure { error ->
            _state.update {
                it.copy(
                    questions = emptyList(),
                    error = error.getErrorMessage(),
                )
            }
        }
    }

    private suspend fun getQuizTopicName(topicCode: Int) {
        topicRepository.getQuizTopicByCode(topicCode).onSuccess { topic ->
            _state.update { it.copy(topBarTitle = topic.name + " Quiz") }
        }.onFailure { error ->
            _event.send(QuizEvent.ShowErrorMessage(error.getErrorMessage()))
        }
    }

    private suspend fun saveUserAnswers() {
        questionRepository.saveUserAnswers(state.value.answers)
            .onFailure { error ->
                _event.send(QuizEvent.ShowErrorMessage(error.getErrorMessage()))
            }
        _event.trySend(QuizEvent.NavigateToResultScreen)
    }
}