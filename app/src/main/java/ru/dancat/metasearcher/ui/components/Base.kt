package ru.dancat.metasearcher.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dancat.metasearcher.ui.theme.FirstTextColor
import ru.dancat.metasearcher.ui.theme.SecondBackground

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BlocksRow(text: String? = null, content: @Composable () -> Unit) {
    Column {
        text?.let {
            Text(
                text,
                color = FirstTextColor,
                fontSize = 15.sp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }
        //Note: Experimental component
        FlowRow(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            content()
        }
    }
}

@Composable
fun SectionBlocks(text: String? = null, content: @Composable () -> Unit) {
    Section {
        BlocksRow(text = text) {
            content()
        }
    }
}

@Composable
fun Section(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 6.dp)
            .fillMaxWidth(0.95f)
            .background(SecondBackground, shape = RoundedCornerShape(10.dp))
    ) {
        content()
    }
}