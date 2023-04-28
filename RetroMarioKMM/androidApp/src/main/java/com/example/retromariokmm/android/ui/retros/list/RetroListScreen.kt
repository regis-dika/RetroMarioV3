package com.example.retromariokmm.android.ui.retros.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.retromariokmm.android.ui.components.RetroItem
import com.example.retromariokmm.utils.ActionState.*
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Success

@Composable
fun RetroScreen(navController: NavController, viewModel: RetroListViewModel = hiltViewModel()) {
    val state = viewModel.retrosState.collectAsState()

    //navigation not good handle if use in when with no launchEffect
    LaunchedEffect(key1 = state.value.connectAction) {
        if (state.value.connectAction == SUCCESS) {
            navController.navigate("life_difficulty_screen")
        }
    }
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
            }
            when (state.value.connectAction) {
                NOT_STARTED -> {}
                PENDING -> CircularProgressIndicator()
                SUCCESS -> {}
                ERROR -> {}
            }

            when (val list = state.value.list) {
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
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(list.value) { retroContainer ->
                                RetroItem(retroContainer = retroContainer, onCardClick = {
                                    viewModel.getActionsFromRetro(retroId = it)
                                }, onResumeClick = {
                                    viewModel.connectToRetro(it)
                                })
                            }
                        }
                    }
            }
        }
    }
}