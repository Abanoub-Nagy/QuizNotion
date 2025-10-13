package com.example.quiznotion.presentation.result.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.quiznotion.R

@Composable
fun ScoreCard(
    modifier: Modifier = Modifier, scorePercentage: Int, correctAnswers: Int, totalQuestions: Int
) {
    val resultText = when (scorePercentage) {
        in 71..100 -> "Congratulations! You did a great job!"
        in 41..70 -> "Good effort! Keep practicing to improve your score."
        else -> "Don't be discouraged! Review the material and try again."
    }
    val imageResId = when (scorePercentage) {
        in 71..100 -> R.drawable.ic_laugh
        in 41..70 -> R.drawable.ic_smiley
        else -> R.drawable.ic_sad
    }
    Card(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
                .size(100.dp),
            painter = painterResource(imageResId),
            contentDescription = "Score Image",
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "you answered $correctAnswers correctly out of $totalQuestions",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp),
            text = resultText,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}