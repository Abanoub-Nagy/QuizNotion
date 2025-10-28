package com.example.quiznotion.data.repository

import com.example.quiznotion.data.mapper.toQuizQuestions
import com.example.quiznotion.data.remote.RemoteQuizDataSource
import com.example.quiznotion.domain.model.QuizQuestion
import com.example.quiznotion.domain.repository.QuizQuestionRepository
import com.example.quiznotion.domain.util.DataError
import com.example.quiznotion.domain.util.Result

class QuizQuestionRepositoryImpl(
    private val remoteQuizDataSource: RemoteQuizDataSource
) : QuizQuestionRepository {

    override suspend fun getQuizQuestions(topicCode: Int): Result<List<QuizQuestion>, DataError> {
        return when (val result = remoteQuizDataSource.getQuizQuestions(
            topicCode = topicCode
        )) {
            is Result.Success -> {
                val questionsDto = result.data
                Result.Success(questionsDto.toQuizQuestions())
            }

            is Result.Failure -> result
        }
    }
}