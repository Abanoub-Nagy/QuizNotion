package com.example.quiznotion.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.quiznotion.domain.model.QuizQuestion
import com.example.quiznotion.presentation.dashboard.DashboardScreen
import com.example.quiznotion.presentation.dashboard.DashboardViewModel
import com.example.quiznotion.presentation.issue_report.IssueReportScreen
import com.example.quiznotion.presentation.issue_report.IssueReportState
import com.example.quiznotion.presentation.quiz.QuizScreen
import com.example.quiznotion.presentation.quiz.QuizViewModel
import com.example.quiznotion.presentation.result.ResultScreen
import com.example.quiznotion.presentation.result.ResultState
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues = PaddingValues(),
) {
    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        startDestination = Route.DashBoardScreen,
    ) {
        val dummyQuestions = List(size = 10) { index ->
            QuizQuestion(
                id = "$index",
                topicCode = 1,
                question = "What is the language for Android Dev?",
                allOptions = listOf("Java", "Python", "Dart", "Kotlin"),
                correctAnswer = "Kotlin",
                explanation = "Some Explanation"
            )
        }
        composable<Route.DashBoardScreen> {
            val viewModel = koinViewModel<DashboardViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            DashboardScreen(
                state = state, onTopicSelected = { topicCode ->
                    navController.navigate(Route.QuizScreen(topicCode))
                })
        }
        composable<Route.QuizScreen> {
            val topicCode = it.toRoute<Route.QuizScreen>().topicCode
            val viewModel = koinViewModel<QuizViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            QuizScreen(state = state, onAction = viewModel::onAction, navigateToDashboardScreen = {
                navController.navigateUp()
            }, navigateToResultScreen = {
                navController.navigate(Route.ResultScreen) {
                    popUpTo<Route.QuizScreen> {
                        inclusive = true
                    }
                }
            })
        }
        composable<Route.ResultScreen> {
            ResultScreen(
                state = ResultState(quizQuestions = dummyQuestions),
                onStartNewQuizClicked = {
                    navController.navigate(Route.DashBoardScreen) {
                        popUpTo<Route.ResultScreen> {
                            inclusive = true
                        }
                    }
                },
                onReportIssueClicked = { questionId ->
                    navController.navigate(Route.IssueReportScreen(questionId))
                })
        }
        composable<Route.IssueReportScreen> {
            IssueReportScreen(
                state = IssueReportState(quizQuestions = dummyQuestions[0]), onBackButtonClicked = {
                    navController.navigateUp()
                })
        }
    }
}