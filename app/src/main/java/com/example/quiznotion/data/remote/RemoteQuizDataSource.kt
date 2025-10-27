package com.example.quiznotion.data.remote

import com.example.quiznotion.data.remote.dto.QuizQuestionDto
import com.example.quiznotion.data.remote.dto.QuizTopicDto

interface RemoteQuizDataSource {
    suspend fun getQuizTopics(): List<QuizTopicDto>?
    suspend fun getQuizQuestions(): List<QuizQuestionDto>?
}