package ru.dancat.metasearcher.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dancat.metasearcher.ui.theme.ThirdBackground

@Composable
fun CommentBlock(bitmap: ImageBitmap, text: String, rate: Double) {
    Box(modifier = Modifier
        .height(75.dp)
        .background(ThirdBackground, shape = RoundedCornerShape(10.dp))
    ) {
        Row {
            Box(
                modifier = Modifier.weight(2f),
            ) {
                Image(
                    bitmap = bitmap,
                    contentDescription = "icon_review",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(10.dp)
                        .clip(CircleShape)
                        .align(Alignment.Center)
                )
            }
            Row(
                modifier = Modifier
                    .weight(5f)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text, fontSize = 16.sp)
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(rate.toString(), textAlign = TextAlign.Center)
            }
        }
    }
}