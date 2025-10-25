package com.example.quiznotion.domain.repository

import com.example.quiznotion.domain.model.QuizTopic

interface QuizTopicRepository {
    suspend fun getQuizTopics(): List<QuizTopic>?
}