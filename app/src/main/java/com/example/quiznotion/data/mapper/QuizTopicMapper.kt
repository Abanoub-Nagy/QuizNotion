package com.example.quiznotion.data.mapper

import com.example.quiznotion.data.local.entity.QuizTopicEntity
import com.example.quiznotion.data.remote.dto.QuizTopicDto
import com.example.quiznotion.data.util.Constant.BASE_URL
import com.example.quiznotion.domain.model.QuizTopic

private fun QuizTopicDto.toQuizTopic() = QuizTopic(
    id = id, name = name, imageUrl = BASE_URL + imageUrl, code = code
)

private fun QuizTopicDto.toQuizTopicEntity() = QuizTopicEntity(
    id = id, name = name, imageUrl = BASE_URL + imageUrl, code = code
)

private fun QuizTopicEntity.entityToQuizTopics() = QuizTopic(
    id = id, name = name, imageUrl = imageUrl, code = code
)

fun List<QuizTopicDto>.toQuizTopics() = map { it.toQuizTopic() }
fun List<QuizTopicDto>.toQuizTopicsEntity() = map { it.toQuizTopicEntity() }
fun List<QuizTopicEntity>.entityToQuizTopics() = map { it.entityToQuizTopics() }