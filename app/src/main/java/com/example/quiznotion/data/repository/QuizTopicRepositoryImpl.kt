package com.example.quiznotion.data.repository

import com.example.quiznotion.data.mapper.toQuizTopics
import com.example.quiznotion.data.remote.HttpClientFactory
import com.example.quiznotion.data.remote.KtorRemoteQuizDataSource
import com.example.quiznotion.domain.model.QuizTopic
import com.example.quiznotion.domain.repository.QuizTopicRepository

class QuizTopicRepositoryImpl : QuizTopicRepository {
    private val httpClient = HttpClientFactory.create()
    private val remoteQuizDataSource = KtorRemoteQuizDataSource(httpClient)
    override suspend fun getQuizTopics(): List<QuizTopic>? {
        val quizTopicsDto = remoteQuizDataSource.getQuizTopics()
        return quizTopicsDto?.toQuizTopics()
    }
}