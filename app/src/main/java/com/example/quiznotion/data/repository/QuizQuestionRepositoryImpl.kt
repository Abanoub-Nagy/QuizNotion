package com.example.quiznotion.data.repository

import com.example.quiznotion.data.mapper.toQuizQuestions
import com.example.quiznotion.data.remote.HttpClientFactory
import com.example.quiznotion.data.remote.KtorRemoteQuizDataSource
import com.example.quiznotion.domain.model.QuizQuestion
import com.example.quiznotion.domain.repository.QuizQuestionRepository

class QuizQuestionRepositoryImpl : QuizQuestionRepository {
    private val httpClient = HttpClientFactory.create()
    private val remoteQuizDataSource = KtorRemoteQuizDataSource(httpClient)
    override suspend fun getQuizQuestions(): List<QuizQuestion>? {
        val quizQuestionsDto = remoteQuizDataSource.getQuizQuestions()
        return quizQuestionsDto?.toQuizQuestions()
    }
}