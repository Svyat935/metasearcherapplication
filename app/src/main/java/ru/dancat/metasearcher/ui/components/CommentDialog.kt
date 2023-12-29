package ru.dancat.metasearcher.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ru.dancat.metasearcher.ui.theme.FirstBackground
import ru.dancat.metasearcher.ui.theme.FirstTextColor
import ru.dancat.metasearcher.ui.theme.SecondBackground

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CommentDialog(
    commentText: MutableState<String>,
    sliderPosition: MutableState<Float>,
    onClose: () -> Unit,
    onCloseSubmit: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FirstBackground)
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(FirstBackground)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Написать комментарий", fontWeight = FontWeight.Bold)
                    IconButton(onClick = { onClose() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                        )
                    }
                }

            OutlinedTextField(
                value = commentText.value,
                onValueChange = { commentText.value = it },
                label = { Text("Введите ваш комментарий") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = FirstBackground,
                    focusedBorderColor = FirstBackground,
                    unfocusedBorderColor = SecondBackground
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            SendRate(sliderPosition = sliderPosition)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = FirstBackground,
                        contentColor = FirstTextColor
                    ),
                    onClick = {
                        onCloseSubmit()
                    }) {
                    Text("Отправить")
                }
            }
        }
    }
}
}