package ru.dancat.metasearcher.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import ru.dancat.metasearcher.ui.theme.FirstBackground
import ru.dancat.metasearcher.ui.theme.SecondBackground
import ru.dancat.metasearcher.ui.theme.FirstTextColor
import ru.dancat.metasearcher.ui.theme.ThirdBackground
import ru.dancat.metasearcher.R
import ru.dancat.metasearcher.ui.components.Section
import ru.dancat.metasearcher.ui.components.SectionBlocks

@Preview(showBackground = true)
@Composable
fun StudioPreview() {
    Studio()
}

@Composable
fun Studio() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(FirstBackground)
        ) {
            ImageTitle(
                bitmap = ImageBitmap.imageResource(R.drawable.icon_test),
                "Title"
            )
            Section {
                Text(
                    "Свернутое описание проекта, свернутое описание проекта, Свернутое описание . . .",
                    color = FirstTextColor,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 6.dp)
                    .fillMaxWidth(0.95f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextBlock("Рейтинг")
                TextBlock("Расписание")
                TextBlock("Цена")
            }
            SectionBlocks(text = "Танцевальные направления") {
                MiniBlock(text = "K-Pop")
                MiniBlock(text = "Swing")
                MiniBlock(text = "Breakdance")
                MiniBlock(text = "Hills")
                MiniBlock(text = "Hip-Hop")
                MiniBlock(text = "Classic")
            }
            SectionBlocks(text = "Адрес: Лиговский Проспект 10А") {
                MiniBlock(text = "Телефон: +78989896070")
                MiniBlock(text = "Telegram")
                MiniBlock(text = "Vk")
                MiniBlock(text = "Instagram")
                MiniBlock(text = "dancegmail.com")
            }
            Reviews()
        }
    }
}

@Composable
fun ImageTitle(bitmap: ImageBitmap, text: String) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
        Image(
            bitmap = bitmap,
            contentDescription = "icon",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
            alpha = 0.8f
        )
        Text(
            text,
            style = TextStyle(fontSize = 50.sp, color = FirstTextColor),
            modifier = Modifier.zIndex(10f)
        )
    }
}

@Composable
fun TextBlock(text: String) {
    Box(
        modifier = Modifier.background(
            SecondBackground, shape = RoundedCornerShape(10.dp)
        )
    ) {
        Text(
            text,
            fontSize = 18.sp,
            color = FirstTextColor,
            modifier = Modifier.padding(7.dp)
        )
    }
}


@Composable
fun MiniBlock(text: String) {
    Box(
        modifier = Modifier
            .padding(bottom = 5.dp)
            .background(FirstTextColor, shape = RoundedCornerShape(10.dp))
    ) {
        Text(
            text, modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
        )
    }
}

@Composable
fun Reviews() {
    Section {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Общая оценка 4.8", color = Color.White)
                Text("Отзывы :", color = Color.White)
            }
            Column(
                modifier = Modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Comment()
                Comment()
                Comment()
            }
        }
    }
}

@Composable
fun Comment() {
    Box(modifier = Modifier
        .height(75.dp)
        .background(ThirdBackground, shape = RoundedCornerShape(10.dp))
    ) {
        Row {
            Box(
                modifier = Modifier.weight(2f),
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.icon_test),
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
                Text("Комментарий 1", fontSize = 16.sp)
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.Bottom
            ) {
                Text("4/5", textAlign = TextAlign.Center)
            }
        }
    }
}