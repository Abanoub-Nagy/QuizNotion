package com.example.quiznotion.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class QuizQuestionDto(
    val id: String,
    val question: String,
    val topicCode: Int,
    @SerialName("incorrectAnswers")
    val inCorrectAnswers: List<String>,
    val correctAnswer: String,
    val explanation: String,
)
