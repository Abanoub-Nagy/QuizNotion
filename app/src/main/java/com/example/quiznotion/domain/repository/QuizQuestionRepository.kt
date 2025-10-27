package com.example.quiznotion.domain.repository

import com.example.quiznotion.domain.model.QuizQuestion
import com.example.quiznotion.domain.util.DataError
import com.example.quiznotion.domain.util.Result

interface QuizQuestionRepository {
    suspend fun getQuizQuestions(): Result<List<QuizQuestion>, DataError>
}