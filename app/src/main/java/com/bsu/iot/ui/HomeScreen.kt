package com.bsu.iot.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bsu.iot.R
import com.bsu.iot.data.Datasource
import com.bsu.iot.model.Station
import com.bsu.iot.ui.theme.IotAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun PullRefreshSetup() {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    var itemCount by remember { mutableStateOf(15) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)
        itemCount += 5
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)

    Box(Modifier.pullRefresh(state)) {
        LazyColumn(Modifier.fillMaxSize()) {
            if (!refreshing) {
                items(itemCount) {
                    ListItem { Text(text = "Item ${itemCount - it}") }
                }
            } else {
                println("Refreshing")
            }
        }
        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun HomeScreen() {
//    val scope = rememberCoroutineScope()
//    val storage = StoreSettings(LocalContext.current)
//    val apiUrl = storage.getApiUrl.collectAsState(initial = "").value
//
//    LaunchedEffect(key1 = apiUrl) {
//        if (apiUrl != null) {
//            val stations = StationsApi.retrofitService.getStationsInfo(apiUrl)
//            println(stations)
//        }
//    }

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)

    Box(Modifier.pullRefresh(state)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = stringResource(id = R.string.home_label) + " page",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(80.dp))
            if (!refreshing) {
                StationList(Datasource().loadStations())
            } else {
                println("Refreshing")
            }
        }
        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun StationList(stationList: List<Station>) {
    LazyColumn {
        items(stationList) { station -> StationListItem(station) }
    }
}

@Composable
fun StationListItem(station: Station) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .height(50.dp), elevation = 5.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = station.name,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 40.dp)
            )
            Text(
                text = "${station.temp} \u2103",
                textAlign = TextAlign.Right,
                modifier = Modifier.padding(end = 40.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun StationListItemPreview() {
    StationListItem(station = Station("Station 1", 25))
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    IotAppTheme {
        HomeScreen()
    }
}