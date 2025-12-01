package com.example.quiznotion.presentation.issue_report

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.quiznotion.domain.repository.QuizQuestionRepository
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

class IssueReportViewModel(
    savedStateHandle: SavedStateHandle,
    private val questionRepository: QuizQuestionRepository,
) : ViewModel() {
    private val questionId = savedStateHandle.toRoute<Route.IssueReportScreen>().questionId
    private val _state = MutableStateFlow(IssueReportState())
    val state = _state.asStateFlow()
    private val _event = Channel<IssueReportEvent>()
    val event = _event.receiveAsFlow()

    init {
        getQuestionById()
    }

    fun onAction(action: IssueReportAction) {
        when (action) {
            IssueReportAction.ExpandQuestionCard -> {
                _state.update { it.copy(isQuestionCardExpanded = !it.isQuestionCardExpanded) }
            }

            is IssueReportAction.SetIssueReportType -> {
                _state.update { it.copy(selectedIssueType = action.issueType) }
            }

            is IssueReportAction.SetOtherIssueText -> {
                _state.update { it.copy(otherIssueText = action.otherIssueText) }
            }

            is IssueReportAction.SetAdditionalComment -> {
                _state.update { it.copy(additionalComments = action.additionalComment) }
            }

            is IssueReportAction.SetEmailForFollowUp -> {
                _state.update { it.copy(emailForNotification = action.emailForFollowUp) }
            }

            IssueReportAction.SubmitReport -> {
//                submitReport()
            }
        }
    }

    private fun getQuestionById() {
        viewModelScope.launch {
            questionRepository.getQuizQuestionById(questionId).onSuccess { question ->
                _state.update { it.copy(quizQuestions = question) }
            }.onFailure { error ->
                _event.send(IssueReportEvent.ShowToast(error.getErrorMessage()))
            }
        }
    }
}