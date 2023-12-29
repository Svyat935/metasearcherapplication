package ru.dancat.metasearcher.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import ru.dancat.metasearcher.ui.theme.FirstBackground
import ru.dancat.metasearcher.ui.theme.FirstTextColor
import ru.dancat.metasearcher.R
import ru.dancat.metasearcher.backend.clients.client
import ru.dancat.metasearcher.backend.models.*
import ru.dancat.metasearcher.ui.components.*

@Composable
fun Studio(studioId: String) {
    val scope = rememberCoroutineScope()

    val content: MutableState<StudioContent?> = remember { mutableStateOf(null) }
    val styles: MutableState<List<Style>?> = remember { mutableStateOf(emptyList()) }
    val metros: MutableState<List<Metro>?> = remember { mutableStateOf(emptyList()) }
    val info: MutableState<List<String>> = remember { mutableStateOf(emptyList()) }
    val comments: MutableState<List<Comment>> = remember { mutableStateOf(emptyList()) }

    val commentText: MutableState<String> = remember { mutableStateOf("") }
    val modalFlag: MutableState<Boolean> = remember { mutableStateOf(false) }
    val rate: MutableState<Float> = remember { mutableStateOf(0.0f) }

    LaunchedEffect(Unit) {
        try {
            val contentDeferred = async { client.getStudioById(studioId) }
            val socialDeferred = async { client.getSocialById(studioId) }
            val commentsDeffered = async { client.getStudioComment(studioId) }

            val content_ = contentDeferred.await()
            val social_ = socialDeferred.await()
            val comments_ = commentsDeffered.await()

            val stylesDeferred = content_.styles.map { async { client.getStyleById(it) } }
            val metrosDeferred = content_.metros.map { async { client.getMetroById(it) } }

            styles.value = stylesDeferred.awaitAll()
            metros.value = metrosDeferred.awaitAll()
            content.value = content_

            val infoResult = mutableListOf(
                content_.website?.let { "Электронная почта: $it" },
                content_.website?.let { "WebSite: $it" },
                content_.inn.let { "ИНН: $it" },
                social_?.youtube?.let { "YouTube $it" },
                social_?.vk?.let { "Vk $it" },
                social_?.whatsApp?.let { "WhatsUp $it" },
                social_?.telegram?.let { "Telegram $it" }
            )

            infoResult += metros.value?.map { "М. ${it.name}" } ?: emptyList()
            info.value = infoResult.filterNotNull()

            comments.value = comments_
        } catch (e: Exception) {
            println(e.message)
            println(e.cause)
            println(e.stackTrace)
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        if (modalFlag.value) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(FirstBackground)
            ) {
                CommentDialog(
                    commentText,
                    rate,
                    onClose = { modalFlag.value = false },
                    onCloseSubmit = {
                        scope.launch {
                            client.createComment(
                                studioId,
                                username = "test",
                                commentText.value,
                                rate.value.toDouble()
                            )
                            comments.value = client.getStudioComment(studioId)
                            modalFlag.value = false
                        }
                    }
                )
            }
        } else {
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
                        content.value?.description ?: "",
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
                    TextBlock(stringResource(id = R.string.rate))
                    TextBlock(stringResource(id = R.string.schedule))
                    TextBlock(stringResource(id = R.string.price))
                }
                SectionBlocks(text = stringResource(id = R.string.dance_styles)) {
                    styles.value?.map { MiniBlock(text = it.name) }
                }
                SectionBlocks(text = stringResource(id = R.string.dance_styles) + content.value?.address) {
                    info.value.map { MiniBlock(text = it) }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(0.90f),
                    horizontalArrangement = Arrangement.Start
                ) {
                    CreateCommentButton {
                        modalFlag.value = true
                    }
                }
                content.value?.rate?.let { Reviews(it, comments = comments) }
            }
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