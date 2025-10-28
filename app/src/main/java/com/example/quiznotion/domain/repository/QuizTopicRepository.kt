package com.example.quiznotion.domain.repository

import com.example.quiznotion.domain.model.QuizTopic
import com.example.quiznotion.domain.util.DataError
import com.example.quiznotion.domain.util.Result

interface QuizTopicRepository {
    suspend fun getQuizTopics(): Result<List<QuizTopic>, DataError>
    suspend fun getQuizTopicByCode(topicCode: Int): Result<QuizTopic, DataError>
}