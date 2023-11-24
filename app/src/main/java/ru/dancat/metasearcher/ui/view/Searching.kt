package ru.dancat.metasearcher.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dancat.metasearcher.R
import ru.dancat.metasearcher.ui.components.BlocksRow
import ru.dancat.metasearcher.ui.components.Section
import ru.dancat.metasearcher.ui.theme.BorderColor
import ru.dancat.metasearcher.ui.theme.FirstBackground
import ru.dancat.metasearcher.ui.theme.FirstTextColor
import ru.dancat.metasearcher.ui.theme.SecondBackground
import java.lang.Math.round

@Preview(showBackground = true)
@Composable
fun SearchingPreview() {
    Searching()
}

@Composable
fun Searching() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(FirstBackground)
        ) {
            SearchBar()
            AveragePrice()
            UpperRate()
            ExposedDropdownMenuSample(
                "Направление танцев",
                listOf(
                    "K-Pop",
                    "Hills",
                    "Classic",
                    "Hip-Hop",
                    "BreakDance"
                )
            )
            ExposedDropdownMenuSample(
                "Ближайшее метро",
                listOf(
                    "Беговая",
                    "Зенит",
                    "Приморская",
                    "Василеостровская",
                    "Гостинный Двор",
                    "Маяковский",
                    "Площадь Александра Невского 1",
                    "Елизаровская",
                    "Ломоносовская",
                    "Пролетарская",
                    "Обухово",
                    "Рыбацкое"
                )
            )
//            BlocksRow {
//                MiniBlock(text = "Hip-Hop")
//                MiniBlock(text = "300-1000 р.")
//                MiniBlock(text = "м.Приморское")
//                MiniBlock(text = "> 4.5")
//            }
//            Divider(modifier = Modifier.padding(bottom = 5.dp), color = FirstTextColor, thickness = 1.dp)
//            StudioBlock("Title 1", 4.5)
//            StudioBlock("Title 2", 4.6)
//            StudioBlock("Title 3", 4.9)
        }
    }
}

@Composable
fun SearchBar() {
    var text by remember { mutableStateOf("") }

    TextField(
        value = "",
        onValueChange = { text = it },
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
            .fillMaxWidth(0.95f)
            .background(SecondBackground, RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun StudioBlock(text: String, rate: Double) {
    Section {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(4f)
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AveragePrice() {
    var sliderPosition by remember { mutableStateOf(0f..100f) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .background(SecondBackground, shape = RoundedCornerShape(10.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Text("Средняя цена абонемента: ", color = FirstTextColor, fontSize = 16.sp)
        RangeSlider(
            values = sliderPosition,
            onValueChange = { range -> sliderPosition = range },
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(
                thumbColor = BorderColor,
                activeTrackColor = FirstTextColor,
                inactiveTickColor = BorderColor
            ),
            modifier = Modifier.fillMaxWidth(0.95f)
        )
        Text(
            "От ${round(sliderPosition.start)} До ${round(sliderPosition.endInclusive)}",
            color = FirstTextColor,
            fontSize = 12.sp
        )
    }
}

@Composable
fun UpperRate() {
    var sliderPosition by remember { mutableStateOf(0f) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .background(SecondBackground, shape = RoundedCornerShape(10.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Text("Оценка выше чем: ", color = FirstTextColor, fontSize = 16.sp)
        Slider(
            value = sliderPosition,
            onValueChange = { range -> sliderPosition = range },
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(
                thumbColor = BorderColor,
                activeTrackColor = FirstTextColor,
                inactiveTickColor = BorderColor
            ),
            modifier = Modifier.fillMaxWidth(0.95f)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropdownMenuSample(title: String, options: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

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
                value = selectedOptionText,
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
                options.filter { it.contains(selectedOptionText, ignoreCase = true) }
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
                                selectedOptionText = selectionOption
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