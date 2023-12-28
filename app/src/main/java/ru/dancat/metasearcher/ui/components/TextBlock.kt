package ru.dancat.metasearcher.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dancat.metasearcher.ui.theme.FirstTextColor
import ru.dancat.metasearcher.ui.theme.SecondBackground

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