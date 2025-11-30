package com.example.quiznotion.data.mapper

import com.example.quiznotion.data.local.entity.QuizQuestionEntity
import com.example.quiznotion.data.remote.dto.QuizQuestionDto
import com.example.quiznotion.domain.model.QuizQuestion

private fun QuizQuestionDto.toQuizQuestion() = QuizQuestion(
    id = id,
    topicCode = topicCode,
    question = question,
    allOptions = (inCorrectAnswers + correctAnswer).shuffled(),
    correctAnswer = correctAnswer,
    explanation = explanation
)

private fun QuizQuestionDto.toQuizQuestionEntity() = QuizQuestionEntity(
    id = id,
    topicCode = topicCode,
    question = question,
    correctAnswer = correctAnswer,
    incorrectAnswers = inCorrectAnswers,
    explanation = explanation
)

fun QuizQuestionEntity.entityToQuizQuestion() = QuizQuestion(
    id = id,
    topicCode = topicCode,
    question = question,
    correctAnswer = correctAnswer,
    allOptions = (incorrectAnswers + correctAnswer).shuffled(),
    explanation = explanation
)

fun List<QuizQuestionDto>.toQuizQuestions() = this.map { it.toQuizQuestion() }

fun List<QuizQuestionDto>.toQuizQuestionsEntity() = map { it.toQuizQuestionEntity() }

fun List<QuizQuestionEntity>.entityToQuizQuestions() = map { it.entityToQuizQuestion() }