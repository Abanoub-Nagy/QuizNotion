package com.example.quiznotion.presentation.dashboard.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier,
    shimmerColors: Color = MaterialTheme.colorScheme.surface,
) {
    val shimmerGradientColors = listOf(
        shimmerColors.copy(0.6f),
        shimmerColors.copy(alpha = 0.2f),
        shimmerColors.copy(0.6f)
    )
    val transition = rememberInfiniteTransition("ShimmerTransition")
    val translateAnim = transition.animateFloat(
        initialValue = 0f, targetValue = 1000f, animationSpec = infiniteRepeatable(
            animation = tween(1000), repeatMode = RepeatMode.Restart
        ), label = "ShimmerEffect"
    )
    val shimmerBrush = Brush.linearGradient(
        colors = shimmerGradientColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value),
    )
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(shimmerBrush)
        )
    }
}

@Preview
@Composable
private fun PreviewShimmerEffect() {
    ShimmerEffect(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    )
}