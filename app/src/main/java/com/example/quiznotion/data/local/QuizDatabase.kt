package com.example.quiznotion.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quiznotion.data.local.converter.OptionListConverters
import com.example.quiznotion.data.local.dao.QuizQuestionDao
import com.example.quiznotion.data.local.dao.QuizTopicDao
import com.example.quiznotion.data.local.dao.UserAnswerDao
import com.example.quiznotion.data.local.entity.QuizQuestionEntity
import com.example.quiznotion.data.local.entity.QuizTopicEntity
import com.example.quiznotion.data.local.entity.UserAnswerEntity

@Database(
    entities = [QuizTopicEntity::class, QuizQuestionEntity::class, UserAnswerEntity::class],
    version = 3,
    exportSchema = false
)

@TypeConverters(
    OptionListConverters::class
)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun quizTopicDao(): QuizTopicDao
    abstract fun quizQuestionDao(): QuizQuestionDao
    abstract fun userAnswerDao(): UserAnswerDao
}