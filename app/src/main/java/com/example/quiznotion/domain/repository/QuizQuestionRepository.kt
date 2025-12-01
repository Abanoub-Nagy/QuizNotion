package com.example.quiznotion.domain.repository

import com.example.quiznotion.domain.model.QuizQuestion
import com.example.quiznotion.domain.model.UserAnswer
import com.example.quiznotion.domain.util.DataError
import com.example.quiznotion.domain.util.Result

interface QuizQuestionRepository {
    suspend fun fetchAndSaveQuizQuestions(
        topicCode: Int,
    ): Result<List<QuizQuestion>, DataError>

    suspend fun getQuizQuestions(): Result<List<QuizQuestion>, DataError>

    suspend fun getQuizQuestionById(questionId: String): Result<QuizQuestion, DataError>

    suspend fun saveUserAnswers(userAnswers: List<UserAnswer>): Result<Unit, DataError>

    suspend fun getUserAnswers(): Result<List<UserAnswer>, DataError>
}