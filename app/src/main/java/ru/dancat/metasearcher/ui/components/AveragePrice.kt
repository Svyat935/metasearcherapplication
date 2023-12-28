package ru.dancat.metasearcher.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RangeSlider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dancat.metasearcher.ui.theme.BorderColor
import ru.dancat.metasearcher.ui.theme.FirstTextColor
import ru.dancat.metasearcher.ui.theme.SecondBackground

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AveragePrice(sliderPosition: MutableState<ClosedFloatingPointRange<Float>>) {

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
            values = sliderPosition.value,
            onValueChange = { range -> sliderPosition.value = range },
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(
                thumbColor = BorderColor,
                activeTrackColor = FirstTextColor,
                inactiveTickColor = BorderColor
            ),
            modifier = Modifier.fillMaxWidth(0.95f)
        )
        Text(
            "От ${Math.round(sliderPosition.value.start)} До ${Math.round(sliderPosition.value.endInclusive)}",
            color = FirstTextColor,
            fontSize = 12.sp
        )
    }
}