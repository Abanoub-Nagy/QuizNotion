package com.example.quiznotion.di

import com.example.quiznotion.data.local.DatabaseFactory
import com.example.quiznotion.data.local.QuizDatabase
import com.example.quiznotion.data.remote.HttpClientFactory
import com.example.quiznotion.data.remote.KtorRemoteQuizDataSource
import com.example.quiznotion.data.remote.RemoteQuizDataSource
import com.example.quiznotion.data.repository.QuizQuestionRepositoryImpl
import com.example.quiznotion.data.repository.QuizTopicRepositoryImpl
import com.example.quiznotion.domain.repository.QuizQuestionRepository
import com.example.quiznotion.domain.repository.QuizTopicRepository
import com.example.quiznotion.presentation.dashboard.DashboardViewModel
import com.example.quiznotion.presentation.quiz.QuizViewModel
import com.example.quiznotion.presentation.result.ResultViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val koinModule = module {
    single { HttpClientFactory.create() }
    singleOf(::KtorRemoteQuizDataSource).bind<RemoteQuizDataSource>()

    single { DatabaseFactory.create(get()) }
    single { get<QuizDatabase>().quizTopicDao() }
    single { get<QuizDatabase>().quizQuestionDao() }
    single { get<QuizDatabase>().userAnswerDao() }

    singleOf(::QuizQuestionRepositoryImpl).bind<QuizQuestionRepository>()
    singleOf(::QuizTopicRepositoryImpl).bind<QuizTopicRepository>()
    viewModelOf(::QuizViewModel)
    viewModelOf(::DashboardViewModel)
    viewModelOf(::ResultViewModel)

}