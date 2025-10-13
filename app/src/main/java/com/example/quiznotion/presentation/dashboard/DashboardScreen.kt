package com.example.quiznotion.presentation.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.example.quiznotion.domain.model.QuizTopic
import com.example.quiznotion.presentation.dashboard.component.HeaderSection
import com.example.quiznotion.presentation.dashboard.component.NameEditDialog
import com.example.quiznotion.presentation.dashboard.component.QuizTopicSection

@Composable
fun DashboardScreen(
    state: DashboardState
) {
    NameEditDialog(
        isDialogOpen = state.isNameEditDialogOpen,
        textFieldValue = state.nameTextFieldValue,
        usernameError = state.usernameError,
        onConfirmClicked = {},
        onDismissRequest = {},
        onTextFieldValueChanged = {},
    )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderSection(
            modifier = Modifier.fillMaxWidth(),
            username = state.username,
            questionsAttempted = state.questionsAttempted,
            correctAnswers = state.correctAnswers,
            onEditProfileClicked = { /* TODO: Handle edit profile click */ })

        QuizTopicSection(
            modifier = Modifier.fillMaxWidth(),
            quizTopics = state.quizTopics,
            isTopicsLoading = state.isTopicsLoading,
            error = state.error,
            onRefreshClicked = { /* TODO: Handle refresh click */ })
    }
}

//@Preview(showBackground = true)
@PreviewScreenSizes
@Composable
private fun PreviewDashboardScreen() {
    val dummyQuizTopic = List(20) {
        QuizTopic(
            id = it.toString(),
            name = "Topic $it",
            imageUrl = "https://picsum.photos/200?random=$it",
            code = it,
        )
    }
    val state = DashboardState(
        username = "John Doe",
        questionsAttempted = 42,
        correctAnswers = 30,
        quizTopics = dummyQuizTopic,
        isTopicsLoading = false,
        isNameEditDialogOpen = false
    )
    DashboardScreen(
        state = state
    )

}