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
                val quizTopicsDto = result.data
                topicDao.clearAllQuizTopics()
                topicDao.insertQuizTopics(quizTopicsDto.toQuizTopicsEntity())
                Result.Success(quizTopicsDto.toQuizTopics())
            }

            is Result.Failure -> {
                val cachedTopic = topicDao.getAllQuizTopics()
                if (cachedTopic.isNotEmpty()) {
                    Result.Success(cachedTopic.entityToQuizTopics())
                } else {
                    result
                }
            }
        }
    }

    override suspend fun getQuizTopicByCode(topicCode: Int): Result<QuizTopic, DataError> {
        return try {
            val topicEntity = topicDao.getQuizTopicByCode(topicCode)
            if (topicEntity != null) {
                Result.Success(topicEntity.entityToQuizTopics())
            } else {
                Result.Failure(DataError.Unknown(errorMessage = "Quiz Topic not found."))
            }
        } catch (e: Exception) {
            Result.Failure(DataError.Unknown(e.message))
        }

    }
}