package com.example.quiznotion.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quiznotion.data.local.dao.QuizTopicDao
import com.example.quiznotion.data.local.entity.QuizTopicEntity

@Database(
    entities = [QuizTopicEntity::class], version = 1, exportSchema = false
)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun quizTopicDao(): QuizTopicDao
}