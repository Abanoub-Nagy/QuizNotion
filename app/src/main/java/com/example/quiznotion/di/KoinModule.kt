package com.example.quiznotion.di

import com.example.quiznotion.data.local.DataStoreFactory
import com.example.quiznotion.data.local.DatabaseFactory
import com.example.quiznotion.data.local.QuizDatabase
import com.example.quiznotion.data.remote.HttpClientFactory
import com.example.quiznotion.data.remote.KtorRemoteQuizDataSource
import com.example.quiznotion.data.remote.RemoteQuizDataSource
import com.example.quiznotion.data.repository.IssueReportRepositoryImpl
import com.example.quiznotion.data.repository.QuizQuestionRepositoryImpl
import com.example.quiznotion.data.repository.QuizTopicRepositoryImpl
import com.example.quiznotion.data.repository.UserPreferencesRepositoryImpl
import com.example.quiznotion.domain.repository.IssueReportRepository
import com.example.quiznotion.domain.repository.QuizQuestionRepository
import com.example.quiznotion.domain.repository.QuizTopicRepository
import com.example.quiznotion.domain.repository.UserPreferencesRepository
import com.example.quiznotion.presentation.dashboard.DashboardViewModel
import com.example.quiznotion.presentation.issue_report.IssueReportViewModel
import com.example.quiznotion.presentation.quiz.QuizViewModel
import com.example.quiznotion.presentation.result.ResultViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val koinModule = module {
    //remote
    single { HttpClientFactory.create() }
    singleOf(::KtorRemoteQuizDataSource).bind<RemoteQuizDataSource>()

    //local database
    single { DatabaseFactory.create(get()) }
    single { DataStoreFactory.create(get()) }
    single { get<QuizDatabase>().quizTopicDao() }
    single { get<QuizDatabase>().quizQuestionDao() }
    single { get<QuizDatabase>().userAnswerDao() }

    //repository
    singleOf(::QuizQuestionRepositoryImpl).bind<QuizQuestionRepository>()
    singleOf(::QuizTopicRepositoryImpl).bind<QuizTopicRepository>()
    singleOf(::IssueReportRepositoryImpl).bind<IssueReportRepository>()
    singleOf(::UserPreferencesRepositoryImpl).bind<UserPreferencesRepository>()

    //viewModel
    viewModelOf(::QuizViewModel)
    viewModelOf(::DashboardViewModel)
    viewModelOf(::ResultViewModel)
    viewModelOf(::IssueReportViewModel)
}