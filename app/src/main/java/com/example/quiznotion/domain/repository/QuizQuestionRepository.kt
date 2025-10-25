package com.example.quiznotion.domain.repository

import com.example.quiznotion.domain.model.QuizQuestion

interface QuizQuestionRepository {
    suspend fun getQuizQuestions(): List<QuizQuestion>?
}