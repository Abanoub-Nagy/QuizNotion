package com.example.quiznotion.data.repository

import com.example.quiznotion.data.mapper.toQuizTopics
import com.example.quiznotion.data.remote.RemoteQuizDataSource
import com.example.quiznotion.domain.model.QuizTopic
import com.example.quiznotion.domain.repository.QuizTopicRepository

class QuizTopicRepositoryImpl(
    private val remoteQuizDataSource: RemoteQuizDataSource
) : QuizTopicRepository {

    override suspend fun getQuizTopics(): List<QuizTopic>? {
        val quizTopicsDto = remoteQuizDataSource.getQuizTopics()
        return quizTopicsDto?.toQuizTopics()
    }
}