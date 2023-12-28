package ru.dancat.metasearcher.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dancat.metasearcher.ui.theme.FirstTextColor

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
