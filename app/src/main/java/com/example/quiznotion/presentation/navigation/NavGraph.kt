package com.example.quiznotion.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.quiznotion.domain.model.QuizQuestion
import com.example.quiznotion.domain.model.QuizTopic
import com.example.quiznotion.presentation.dashboard.DashboardScreen
import com.example.quiznotion.presentation.dashboard.DashboardState
import com.example.quiznotion.presentation.issue_report.IssueReportScreen
import com.example.quiznotion.presentation.issue_report.IssueReportState
import com.example.quiznotion.presentation.quiz.QuizScreen
import com.example.quiznotion.presentation.quiz.QuizState
import com.example.quiznotion.presentation.result.ResultScreen
import com.example.quiznotion.presentation.result.ResultState

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
            val dummyQuizTopic = List(20) {
                QuizTopic(
                    id = it.toString(),
                    name = "Topic $it",
                    imageUrl = "https://picsum.photos/200?random=$it",
                    code = it,
                )
            }
            DashboardScreen(
                state = DashboardState(quizTopics = dummyQuizTopic),
                onTopicSelected = { topicCode ->
                    navController.navigate(Route.QuizScreen(topicCode))
                })
        }
        composable<Route.QuizScreen> {
            val topicCode = it.toRoute<Route.QuizScreen>().topicCode
            QuizScreen(
                state = QuizState(
                topBarTitle = "Topic $topicCode",
                questions = dummyQuestions,
            ), navigateToDashboardScreen = {
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