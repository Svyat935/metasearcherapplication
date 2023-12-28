package ru.dancat.metasearcher.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import ru.dancat.metasearcher.R
import ru.dancat.metasearcher.backend.models.Comment
import ru.dancat.metasearcher.ui.components.Section

@Composable
fun Reviews(averageRate: Double, comments: MutableState<List<Comment>>) {
    Section {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Общая оценка $averageRate", color = Color.White)
                Text("Отзывы :", color = Color.White)
            }
            Column(
                modifier = Modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                comments.value.map {
                    CommentBlock(
                        ImageBitmap.imageResource(R.drawable.icon_test),
                        text = it.comment,
                        rate = it.rate
                    )
                }
            }
        }
    }
}