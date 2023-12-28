package ru.dancat.metasearcher.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dancat.metasearcher.R
import ru.dancat.metasearcher.ui.theme.FirstTextColor

@Composable
fun StudioBlock(text: String, rate: Float, onClick: () -> Unit = {}) {
    Section {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(4f)
                    .clickable { onClick() }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                rate.toString(),
                                style = TextStyle(
                                    color = FirstTextColor.copy(0.75f),
                                    fontSize = 16.sp,
                                )
                            )
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = null,
                                tint = FirstTextColor.copy(0.75f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(text, color = FirstTextColor, fontSize = 24.sp)
                    }
                }
            }
            Box(
                modifier = Modifier
                    .height(75.dp)
                    .weight(1f)
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.icon_test),
                    contentDescription = "icon_review",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(10.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}