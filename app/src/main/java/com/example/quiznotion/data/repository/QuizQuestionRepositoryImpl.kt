package com.example.quiznotion.data.repository

import com.example.quiznotion.data.mapper.toQuizQuestions
import com.example.quiznotion.data.remote.RemoteQuizDataSource
import com.example.quiznotion.domain.model.QuizQuestion
import com.example.quiznotion.domain.repository.QuizQuestionRepository

class QuizQuestionRepositoryImpl(
    private val remoteQuizDataSource: RemoteQuizDataSource
) : QuizQuestionRepository {

    override suspend fun getQuizQuestions(): List<QuizQuestion>? {
        val quizQuestionsDto = remoteQuizDataSource.getQuizQuestions()
        return quizQuestionsDto?.toQuizQuestions()
    }
}