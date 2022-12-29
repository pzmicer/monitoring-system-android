package com.bsu.iot.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bsu.iot.R
import com.bsu.iot.data.StoreSettings
import com.bsu.iot.ui.theme.IotAppTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@Composable
fun OptionsScreen() {
    val scope = rememberCoroutineScope()
    val storage = StoreSettings(LocalContext.current)

    Column(
        modifier = Modifier
            .padding(top = 40.dp)
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = stringResource(id = R.string.settings_label),
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(30.dp))

        val apiUrl = storage.getApiUrl.collectAsState(initial = null).value
        val text = remember { mutableStateOf(apiUrl ?: "undefined") }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = text.value,
                label = { Text("API URL") },
                onValueChange = { text.value = it },
                modifier = Modifier.padding(start = 10.dp),
            )
            Button(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .wrapContentSize(),
                onClick = {
                    if (apiUrl != null) {
                        scope.launch {
                            storage.saveApiUrl(apiUrl)
                        }
                    }
                }) {
                Text(
                    style = MaterialTheme.typography.subtitle1,
                    text = "Save",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OptionsScreenPreview() {
    IotAppTheme {
        OptionsScreen()
    }
}