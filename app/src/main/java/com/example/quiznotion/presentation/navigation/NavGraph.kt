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
import com.example.quiznotion.presentation.dashboard.DashboardScreen
import com.example.quiznotion.presentation.dashboard.DashboardViewModel
import com.example.quiznotion.presentation.issue_report.IssueReportScreen
import com.example.quiznotion.presentation.issue_report.IssueReportViewModel
import com.example.quiznotion.presentation.quiz.QuizScreen
import com.example.quiznotion.presentation.quiz.QuizViewModel
import com.example.quiznotion.presentation.result.ResultScreen
import com.example.quiznotion.presentation.result.ResultViewModel
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
        composable<Route.DashBoardScreen> {
            val viewModel = koinViewModel<DashboardViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            DashboardScreen(
                state = state,
                onAction = viewModel::onAction,
                onTopicSelected = { topicCode ->
                    navController.navigate(Route.QuizScreen(topicCode))
                })
        }
        composable<Route.QuizScreen> {
            val viewModel = koinViewModel<QuizViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            QuizScreen(state = state, onAction = viewModel::onAction, navigateToDashboardScreen = {
                navController.navigateUp()
            }, event = viewModel.event, navigateToResultScreen = {
                navController.navigate(Route.ResultScreen) {
                    popUpTo<Route.QuizScreen> {
                        inclusive = true
                    }
                }
            })
        }
        composable<Route.ResultScreen> {
            val viewModel = koinViewModel<ResultViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            ResultScreen(state = state, event = viewModel.event, onStartNewQuizClicked = {
                navController.navigate(Route.DashBoardScreen) {
                    popUpTo<Route.ResultScreen> {
                        inclusive = true
                    }
                }
            }, onReportIssueClicked = { questionId ->
                navController.navigate(Route.IssueReportScreen(questionId))
            })
        }
        composable<Route.IssueReportScreen> {
            val viewModel = koinViewModel<IssueReportViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            IssueReportScreen(
                state = state,
                onAction = viewModel::onAction,
                event = viewModel.event,
                navigateUp = {
                    navController.navigateUp()
                })
        }
    }
}