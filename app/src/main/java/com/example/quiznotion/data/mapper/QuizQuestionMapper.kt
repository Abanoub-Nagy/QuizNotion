package com.example.quiznotion.data.mapper

import com.example.quiznotion.data.remote.dto.QuizQuestionDto
import com.example.quiznotion.domain.model.QuizQuestion

fun QuizQuestionDto.toQuizQuestion() = QuizQuestion(
    id = id,
    topicCode = topicCode,
    question = question,
    allOptions = (inCorrectAnswers + correctAnswer).shuffled(),
    correctAnswer = correctAnswer,
    explanation = explanation
)

fun List<QuizQuestionDto>.toQuizQuestions() = this.map { it.toQuizQuestion() }