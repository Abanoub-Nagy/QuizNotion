package com.example.quiznotion.data.repository

import com.example.quiznotion.data.local.dao.QuizTopicDao
import com.example.quiznotion.data.mapper.entityToQuizTopics
import com.example.quiznotion.data.mapper.toQuizTopics
import com.example.quiznotion.data.mapper.toQuizTopicsEntity
import com.example.quiznotion.data.remote.RemoteQuizDataSource
import com.example.quiznotion.domain.model.QuizTopic
import com.example.quiznotion.domain.repository.QuizTopicRepository

class QuizTopicRepositoryImpl(
    private val remoteQuizDataSource: RemoteQuizDataSource, private val topicDao: QuizTopicDao
) : QuizTopicRepository {

    override suspend fun getQuizTopics(): List<QuizTopic>? {
        val quizTopicsDto = remoteQuizDataSource.getQuizTopics()
        return if (quizTopicsDto != null) {
            topicDao.clearAllQuizTopics()
            topicDao.insertQuizTopics(quizTopicsDto.toQuizTopicsEntity())
            quizTopicsDto.toQuizTopics()
        } else {
            val cachedTopics = topicDao.getAllQuizTopics()
            if (cachedTopics.isNotEmpty()) {
                cachedTopics.entityToQuizTopics()
            } else {
                null
            }
        }
    }
}