package com.example.quiznotion.data.mapper

import com.example.quiznotion.data.local.entity.UserAnswerEntity
import com.example.quiznotion.domain.model.UserAnswer

private fun UserAnswer.toUserAnswerEntity() = UserAnswerEntity(
    questionId = questionId,
    selectedOption = selectedAnswer
)

private fun UserAnswerEntity.toUserAnswer() = UserAnswer(
    questionId = questionId,
    selectedAnswer = selectedOption
)

fun List<UserAnswerEntity>.toUserAnswers() = map { it.toUserAnswer() }

fun List<UserAnswer>.toUserAnswersEntity() = map { it.toUserAnswerEntity() }