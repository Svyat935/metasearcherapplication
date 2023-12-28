package ru.dancat.metasearcher.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dancat.metasearcher.ui.theme.FirstTextColor
import ru.dancat.metasearcher.ui.theme.SecondBackground

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropdownMenuSample(
    title: String,
    options: List<String>,
    currentValue: MutableState<String>
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .background(SecondBackground, shape = RoundedCornerShape(10.dp))
        ) {
            TextField(
                value = currentValue.value,
                onValueChange = {},
                textStyle = TextStyle(color = FirstTextColor),
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                label = { Text(title, color = FirstTextColor, fontSize = 16.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SecondBackground, shape = RoundedCornerShape(10.dp))
            )
            val filteringOptions =
                options.filter { it.contains(currentValue.value, ignoreCase = true) }
            if (filteringOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SecondBackground)
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                currentValue.value = selectionOption
                                expanded = false
                            }
                        ) {
                            Text(text = selectionOption, color = FirstTextColor)
                        }
                    }
                }
            }
        }
    }
}