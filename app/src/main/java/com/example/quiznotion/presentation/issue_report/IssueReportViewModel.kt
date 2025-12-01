package com.example.quiznotion.presentation.issue_report

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.quiznotion.domain.model.IssueReport
import com.example.quiznotion.domain.repository.IssueReportRepository
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
    private val issueReportRepository: IssueReportRepository
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
                submitReport()
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

    private fun submitReport() {
        viewModelScope.launch {
            val state = state.value

            val errorMessage = validateInputs(state)
            errorMessage?.let {
                _event.send(IssueReportEvent.ShowToast(it))
                return@launch
            }

            val issueType = if (state.selectedIssueType == IssueType.OTHER) {
                state.otherIssueText
            } else state.selectedIssueType.name

            val report = IssueReport(
                questionId = questionId,
                issueType = issueType,
                additionalComment = state.additionalComments.ifBlank { null },
                userEmail = state.emailForNotification.ifBlank { null },
                timestampMillis = System.currentTimeMillis()
            )

            issueReportRepository.insertIssueReport(report).onSuccess {
                _event.send(IssueReportEvent.ShowToast("Report Submitted Successfully"))
                _event.send(IssueReportEvent.NavigateUp)
            }.onFailure { error ->
                _event.send(IssueReportEvent.ShowToast(error.getErrorMessage()))
            }
        }
    }

    private fun validateInputs(state: IssueReportState): String? {
        return when {
            state.selectedIssueType == IssueType.OTHER && state.otherIssueText.isBlank() -> {
                "Please select an issue type."
            }

            state.additionalComments.isNotBlank() && state.additionalComments.length < 5 -> {
                "Additional comment must be at least 5 character long"
            }

            state.emailForNotification.isNotBlank() && !state.emailForNotification.isValidEmail() -> {
                "Invalid Email address"
            }

            else -> null
        }
    }

    private fun String.isValidEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}