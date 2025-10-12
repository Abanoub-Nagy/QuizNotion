package com.example.quiznotion.presentation.dashboard.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.quiznotion.R

@Composable
fun TopicCard(
    modifier: Modifier = Modifier,
    topicName: String = "Topic Name",
    imageUrl: String = "",
) {
    Box {
        Card(
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Bottom,
            ) {
                Icon(
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .size(20.dp),
                    painter = painterResource(R.drawable.ic_play),
                    contentDescription = topicName,
                    tint = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = topicName, style = MaterialTheme.typography.titleLarge
                )
            }
        }
        TopicImage(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 10.dp)
                .size(100.dp)
                .offset(y = (-20).dp),
            imageUrl = imageUrl,
        )
    }
}

@Composable
fun TopicImage(
    modifier: Modifier = Modifier,
    imageUrl: String = "",
) {
    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context).data(imageUrl).crossfade(true).build()
    AsyncImage(
        modifier = modifier,
        model = imageRequest,
        contentDescription = "Topic Image",
        alignment = Alignment.TopCenter,
        placeholder = painterResource(R.drawable.img_topic_placeholder),
        error = painterResource(R.drawable.img_topic_placeholder),
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewTopicCard() {
    TopicCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        topicName = "Mathematics",
        imageUrl = "https://images.unsplash.com/photo-1503676260728-1c00da094a0b?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80"
    )
}