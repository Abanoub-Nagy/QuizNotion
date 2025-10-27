package com.example.quiznotion.data.remote

import com.example.quiznotion.data.remote.dto.QuizQuestionDto
import com.example.quiznotion.data.remote.dto.QuizTopicDto
import com.example.quiznotion.data.util.Constant.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class KtorRemoteQuizDataSource(
    private val httpClient: HttpClient
) : RemoteQuizDataSource {

    override suspend fun getQuizTopics(): List<QuizTopicDto>? {
        return try {
            val response = httpClient.get(urlString = "$BASE_URL/quiz/topics")
            response.body<List<QuizTopicDto>>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getQuizQuestions(): List<QuizQuestionDto>? {
        return try {
            val response = httpClient.get(urlString = "$BASE_URL/quiz/questions/random")
            response.body<List<QuizQuestionDto>>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}