package com.example.quiznotion.data.remote

import com.example.quiznotion.data.remote.dto.QuizQuestionDto
import com.example.quiznotion.data.remote.dto.QuizTopicDto
import com.example.quiznotion.domain.util.DataError
import com.example.quiznotion.domain.util.Result

interface RemoteQuizDataSource {
    suspend fun getQuizTopics(): Result<List<QuizTopicDto>, DataError>
    suspend fun getQuizQuestions(topicCode: Int): Result<List<QuizQuestionDto>, DataError>
}