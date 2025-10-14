package com.example.quiznotion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.quiznotion.presentation.navigation.NavGraph
import com.example.quiznotion.presentation.theme.QuizNotionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizNotionTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}