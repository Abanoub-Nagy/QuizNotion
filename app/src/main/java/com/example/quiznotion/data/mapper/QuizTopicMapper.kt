package com.example.quiznotion.data.mapper

import com.example.quiznotion.data.remote.dto.QuizTopicDto
import com.example.quiznotion.data.util.Constant.BASE_URL
import com.example.quiznotion.domain.model.QuizTopic

fun QuizTopicDto.toQuizTopic() = QuizTopic(
    id = id, name = name, imageUrl = BASE_URL + imageUrl, code = code
)

fun List<QuizTopicDto>.toQuizTopics() = map { it.toQuizTopic() }