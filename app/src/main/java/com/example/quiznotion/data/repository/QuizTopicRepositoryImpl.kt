package com.example.quiznotion.data.repository

import com.example.quiznotion.data.local.dao.QuizTopicDao
import com.example.quiznotion.data.mapper.entityToQuizTopics
import com.example.quiznotion.data.mapper.toQuizTopics
import com.example.quiznotion.data.mapper.toQuizTopicsEntity
import com.example.quiznotion.data.remote.RemoteQuizDataSource
import com.example.quiznotion.domain.model.QuizTopic
import com.example.quiznotion.domain.repository.QuizTopicRepository
import com.example.quiznotion.domain.util.DataError
import com.example.quiznotion.domain.util.Result

class QuizTopicRepositoryImpl(
    private val remoteQuizDataSource: RemoteQuizDataSource, private val topicDao: QuizTopicDao
) : QuizTopicRepository {

    override suspend fun getQuizTopics(): Result<List<QuizTopic>, DataError> {
        return when (val result = remoteQuizDataSource.getQuizTopics()) {
            is Result.Success -> {
                val topics = result.data
                topicDao.clearAllQuizTopics()
                topicDao.insertQuizTopics(topics.toQuizTopicsEntity())
                return Result.Success(topics.toQuizTopics())
            }

            is Result.Failure -> {
                val cachedTopics = topicDao.getAllQuizTopics()
                if (cachedTopics.isNotEmpty()) {
                    Result.Success(cachedTopics.entityToQuizTopics())
                } else {
                    result
                }
            }
        }
    }

    override suspend fun getQuizTopicByCode(topicCode: Int): Result<QuizTopic, DataError> {
        return try {
            val cachedTopic = topicDao.getQuizTopicByCode(topicCode)
            if (cachedTopic != null) {
                Result.Success(cachedTopic.entityToQuizTopics())
            } else {
                Result.Failure(DataError.Unknown("Topic with code $topicCode not found"))
            }
        } catch (e: Exception) {
            Result.Failure(DataError.Unknown(e.message))
        }

    }
}