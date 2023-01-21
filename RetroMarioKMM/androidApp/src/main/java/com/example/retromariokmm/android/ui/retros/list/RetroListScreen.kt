package com.example.retromariokmm.android.ui.retros.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Success

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RetroScreen(navController: NavController, viewModel: RetroListViewModel = hiltViewModel()) {
    val state = viewModel.retrosState.collectAsState(initial = Loading())
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(6.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedButton(onClick = {
                    navController.navigate("retro_creation")
                }) {
                    Text(text = "New Retro")
                }
                OutlinedButton(onClick = {
                    //navController.navigate("life_difficulty_screen")
                }) {
                    Text(text = "Go to Updated HealthyScreen")
                }
            }

            when (val list = state.value) {
                is Error -> Snackbar() {
                    Text(text = list.msg)
                }
                is Loading -> {
                    CircularProgressIndicator()
                }
                is Success ->
                    if (list.value.isEmpty()) {
                        Text(modifier = Modifier.fillMaxSize(), text = "NO retro")
                    } else {
                        LazyColumn() {
                            items(list.value, key = {
                                it.retroId
                            }) { retro ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(6.dp)
                                ) {
                                    Column {
                                        Text(text = retro.title)
                                        Text(text = retro.description)
                                    }
                                }
                            }
                        }
                    }
            }
        }
    }
}