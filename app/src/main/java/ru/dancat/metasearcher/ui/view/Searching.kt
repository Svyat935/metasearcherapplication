package ru.dancat.metasearcher.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ru.dancat.metasearcher.backend.clients.client
import ru.dancat.metasearcher.backend.models.Metro
import ru.dancat.metasearcher.backend.models.StudioLite
import ru.dancat.metasearcher.backend.models.StudioRequest
import ru.dancat.metasearcher.backend.models.Style
import ru.dancat.metasearcher.ui.components.*
import ru.dancat.metasearcher.ui.theme.FirstBackground
import ru.dancat.metasearcher.ui.theme.FirstTextColor
import kotlin.math.roundToInt

@Preview(showBackground = true)
@Composable
fun SearchingPreview() {
    Searching(rememberNavController())
}

@Composable
fun Searching(navController: NavController) {
    val metros = remember { mutableStateOf<List<Metro>>(emptyList()) }
    val directions = remember { mutableStateOf<List<Style>>(emptyList()) }
    val studios = remember { mutableStateOf<List<StudioLite>>(emptyList()) }

    val searchFlag = remember { mutableStateOf(false) }
    val searchText = remember { mutableStateOf("") }
    val averagePrice = remember { mutableStateOf(0f..100f) }
    val averageRate = remember { mutableStateOf(0f) }
    val selectedMetro = remember { mutableStateOf("") }
    val selectedDirection = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        val job = scope.launch {
            try {
                metros.value = client.getMetros()
                directions.value = client.getStyles()
                val result = client.getStudios(StudioRequest())
                studios.value = result.content
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
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(FirstBackground)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .background(FirstBackground)
            ) {
                SearchBar(searchText,
                    onSearchSubmit = {
                        scope.launch {
                            val m = metros.value.find { it.name == selectedMetro.value }?.id
                            var mList = emptyList<String>()
                            if (m != null){
                                mList = listOf("\"$m\"")
                            }

                            val s = directions.value.find { it.name == selectedDirection.value }?.id
                            var sList = emptyList<String>()
                            if (s != null){
                                sList = listOf("\"$s\"")
                            }

                            val result = client.getStudios(StudioRequest(
                                minimalRate = averageRate.value.toDouble(),
                                metroList = mList,
                                stylesList = sList,
                                searchCriteria = searchText.value,
                            ))
                            studios.value = result.content
                            searchFlag.value = false
                        }
                    },
                    onFocus = {
                        searchFlag.value = true
                    }
                )
                DownArrowButton{
                    searchFlag.value = !searchFlag.value
                }
            }
            if (
                selectedDirection.value != "" ||
                selectedMetro.value != "" ||
                averagePrice.value != 0f..100f ||
                averageRate.value > 0
            ){
                BlocksRow {
                    if (selectedDirection.value != "") {
                        MiniBlock(text = selectedDirection.value)
                    }
                    if (averagePrice.value != 0f..100f) {
                        MiniBlock(
                            text = "${averagePrice.value.start.toInt()}-" +
                                    "${averagePrice.value.endInclusive.toInt()} р."
                        )
                    }
                    if (selectedMetro.value != "") {
                        MiniBlock(text = "м. ${selectedMetro.value}")
                    }
                    if (averageRate.value > 0) {
                        MiniBlock(text = "> ${averageRate.value.roundToInt()}")
                    }
                }
            }
            Divider(
                modifier = Modifier.padding(bottom = 5.dp),
                color = FirstTextColor,
                thickness = 1.dp
            )
            AnimatedVisibility(searchFlag.value) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(FirstBackground)
                ) {
                    AveragePrice(averagePrice)
                    UpperRate(averageRate)
                    ExposedDropdownMenuSample(
                        "Направление танцев",
                        directions.value.map { direct -> direct.name },
                        selectedDirection
                    )
                    ExposedDropdownMenuSample(
                        "Ближайшее метро",
                        metros.value.map { metro -> metro.name },
                        selectedMetro
                    )
                }
            }
            AnimatedVisibility(!searchFlag.value) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(FirstBackground)
                ) {
                    studios.value.map { StudioBlock(text = it.name, rate = it.rate.toFloat()){
                        navController.navigate("studio/${it.id}")
                    } }
                }
            }
        }
    }
}

