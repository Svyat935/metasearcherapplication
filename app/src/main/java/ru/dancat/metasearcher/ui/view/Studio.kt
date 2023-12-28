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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import ru.dancat.metasearcher.ui.theme.FirstBackground
import ru.dancat.metasearcher.ui.theme.SecondBackground
import ru.dancat.metasearcher.ui.theme.FirstTextColor
import ru.dancat.metasearcher.ui.theme.ThirdBackground
import ru.dancat.metasearcher.R
import ru.dancat.metasearcher.backend.clients.client
import ru.dancat.metasearcher.backend.models.Comment
import ru.dancat.metasearcher.backend.models.Metro
import ru.dancat.metasearcher.backend.models.StudioContent
import ru.dancat.metasearcher.backend.models.Style
import ru.dancat.metasearcher.ui.components.*

//@Preview(showBackground = true)
//@Composable
//fun StudioPreview() {
//    Studio("123")
//}

@Composable
fun Studio(studioId: String) {
    val scope = rememberCoroutineScope()
    val content: MutableState<StudioContent?> = remember { mutableStateOf(null) }
    val styles: MutableState<List<Style>?> = remember { mutableStateOf(emptyList()) }
    val metros: MutableState<List<Metro>?> = remember { mutableStateOf(emptyList()) }
    val info: MutableState<List<String>> = remember { mutableStateOf(emptyList()) }
    val comments: MutableState<List<Comment>> = remember { mutableStateOf(emptyList()) }

    DisposableEffect(Unit) {
        val job = scope.launch {
            try {
                content.value = client.getStudioById(studioId)
                styles.value = content.value?.styles?.map {
                    client.getStyleById(it)
                }
                metros.value = content.value?.metros?.map {
                    client.getMetroById(it)
                }
                comments.value = client.getStudioComment(studioId)
                val infoResult = mutableListOf(
                    "Электронная почта: ${content.value?.email}",
                    "WebSite: ${content.value?.website}",
                    "ИНН ${content.value?.inn}"
                )
                infoResult += metros.value?.map { "М. ${it.name}" } ?: emptyList()
                info.value = infoResult.toList()
            } catch (e: Exception) {
                println(e.message)
                println(e.cause)
                println(e.stackTrace)
            }
        }

        onDispose {
            job.cancel()
        }
    }

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
                text = content.value?.name ?: ""
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
                styles.value?.map { MiniBlock(text = it.name) }
            }
            SectionBlocks(text = "Адрес: ${content.value?.address}") {
                info.value.map { MiniBlock(text = it) }
            }
            content.value?.rate?.let { Reviews(it, comments = comments) }
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