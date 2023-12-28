package ru.dancat.metasearcher.ui.components

import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dancat.metasearcher.ui.theme.FirstTextColor
import ru.dancat.metasearcher.ui.theme.SecondBackground

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    searchText: MutableState<String>,
    onSearchSubmit: () -> Unit,
    onFocus: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = searchText.value,
        onValueChange = {
            searchText.value = it
        },
        placeholder = { Text("Search", color = FirstTextColor) },
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = null,
                tint = FirstTextColor
            )
        },
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(0.8f)
            .background(SecondBackground, RoundedCornerShape(10.dp))
            .onFocusChanged {
                if (it.isFocused) {
                    onFocus()
                }
            },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchSubmit()
                keyboardController?.hide()
            },
        ),
        textStyle = TextStyle(color = FirstTextColor)
    )
}

@Composable
fun SearchBarDemo() {
    val searchText = remember { mutableStateOf("") }

    SearchBar(
        searchText = searchText,
        onSearchSubmit = {
            println("Test succesfull")
        },
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarDemoPreview() {
    SearchBarDemo()
}