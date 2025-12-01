package com.example.quiznotion.presentation.quiz.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OptionItem(
    modifier: Modifier = Modifier,
    optionText: String,
    isSelected: Boolean,
    onOptionSelected: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onOptionSelected() }
            .border(
                width = if (isSelected) 2.dp else 1.dp, color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                }, shape = MaterialTheme.shapes.small
            ), colors = CardDefaults.cardColors(
        containerColor = if (isSelected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        }
    )) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected, onClick = onOptionSelected
            )
            Text(
                text = optionText, style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}