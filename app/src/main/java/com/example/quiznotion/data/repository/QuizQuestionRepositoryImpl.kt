package com.example.quiznotion.data.repository

import com.example.quiznotion.data.local.dao.QuizQuestionDao
import com.example.quiznotion.data.local.dao.UserAnswerDao
import com.example.quiznotion.data.mapper.entityToQuizQuestion
import com.example.quiznotion.data.mapper.entityToQuizQuestions
import com.example.quiznotion.data.mapper.toQuizQuestions
import com.example.quiznotion.data.mapper.toQuizQuestionsEntity
import com.example.quiznotion.data.mapper.toUserAnswers
import com.example.quiznotion.data.mapper.toUserAnswersEntity
import com.example.quiznotion.data.remote.RemoteQuizDataSource
import com.example.quiznotion.domain.model.QuizQuestion
import com.example.quiznotion.domain.model.UserAnswer
import com.example.quiznotion.domain.repository.QuizQuestionRepository
import com.example.quiznotion.domain.util.DataError
import com.example.quiznotion.domain.util.Result

class QuizQuestionRepositoryImpl(
    private val remoteQuizDataSource: RemoteQuizDataSource,
    private val questionDao: QuizQuestionDao,
    private val answerDao: UserAnswerDao,
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

    override suspend fun getQuizQuestionById(questionId: String): Result<QuizQuestion, DataError> {
        return try {
            val questionEntity = questionDao.getQuizQuestionById(questionId)
            if (questionEntity != null) {
                Result.Success(questionEntity.entityToQuizQuestion())
            } else {
                Result.Failure(DataError.Unknown(errorMessage = "Quiz Question not found"))
            }
        } catch (e: Exception) {
            Result.Failure(DataError.Unknown(e.message))
        }
    }

    override suspend fun saveUserAnswers(userAnswers: List<UserAnswer>): Result<Unit, DataError> {
        return try {
            val answersEntity = userAnswers.toUserAnswersEntity()
            answerDao.insertUserAnswers(answersEntity)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(DataError.Unknown(e.message))
        }
    }

    override suspend fun getUserAnswers(): Result<List<UserAnswer>, DataError> {
        return try {
            val answersEntity = answerDao.getAllUserAnswers()
            if (answersEntity.isNotEmpty()) {
                Result.Success(answersEntity.toUserAnswers())
            } else {
                Result.Failure(DataError.Unknown(errorMessage = "No User Answers found"))
            }
        } catch (e: Exception) {
            Result.Failure(DataError.Unknown(e.message))
        }
    }
}