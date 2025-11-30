package com.example.quiznotion.data.repository

import com.example.quiznotion.data.local.dao.QuizQuestionDao
import com.example.quiznotion.data.mapper.entityToQuizQuestions
import com.example.quiznotion.data.mapper.toQuizQuestions
import com.example.quiznotion.data.mapper.toQuizQuestionsEntity
import com.example.quiznotion.data.remote.RemoteQuizDataSource
import com.example.quiznotion.domain.model.QuizQuestion
import com.example.quiznotion.domain.repository.QuizQuestionRepository
import com.example.quiznotion.domain.util.DataError
import com.example.quiznotion.domain.util.Result

class QuizQuestionRepositoryImpl(
    private val remoteQuizDataSource: RemoteQuizDataSource,
    private val questionDao: QuizQuestionDao,
) : QuizQuestionRepository {

    override suspend fun fetchAndSaveQuizQuestions(topicCode: Int): Result<List<QuizQuestion>, DataError> {
        return when (val result = remoteQuizDataSource.getQuizQuestions(
            topicCode = topicCode
        )) {
            is Result.Success -> {
                val questionsDto = result.data
                questionDao.clearAllQuizQuestions()
                questionDao.insertQuizQuestions(questionsDto.toQuizQuestionsEntity())
                Result.Success(questionsDto.toQuizQuestions())
            }

            is Result.Failure -> result
        }
    }

    override suspend fun getQuizQuestions(): Result<List<QuizQuestion>, DataError> {
        return try {
            val questionsEntity = questionDao.getAllQuizQuestions()
            if (questionsEntity.isNotEmpty()) {
                Result.Success(questionsEntity.entityToQuizQuestions())
            } else {
                Result.Failure(DataError.Unknown(errorMessage = "No Quiz Questions Found."))
            }
        } catch (e: Exception) {
            Result.Failure(DataError.Unknown(e.message))
        }
    }
}