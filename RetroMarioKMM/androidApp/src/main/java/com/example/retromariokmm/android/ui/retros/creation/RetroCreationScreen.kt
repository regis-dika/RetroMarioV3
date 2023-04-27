package com.example.retromariokmm.android.ui.retros.creation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.retromariokmm.android.ui.components.DoubleTextFieldItem
import com.example.retromariokmm.utils.ActionState.*
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Success

@Composable
fun RetroCreationScreen(
    navController: NavController,
    state: RetroCreationContainer,
    createRetroEvent: (() -> Unit),
    addToRetroEvent: (() -> Unit),
    onSprintTitleEvent: ((String) -> Unit),
    onSprintDescriptionEvent: ((String) -> Unit),
) {
    val clipboardManager = LocalClipboardManager.current

    //navigation not good handle if use in when with no launchEffect
    LaunchedEffect(key1 = state.addMeToTheRetroAction) {
        if (state.addMeToTheRetroAction == SUCCESS) {
            navController.navigate("life_difficulty_screen")
        }
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DoubleTextFieldItem(
            firstEdiTitleValue = Pair("Sprint title", state.sprintTitle),
            onFirstEdtChange = { onSprintTitleEvent.invoke(it) },
            secondEdiTitleValue = Pair("Sprint description", state.sprintDescription),
            onSecondEdtChange = { onSprintDescriptionEvent.invoke(it) },
            isEnable = state.retroId == null || state.retroId is Error,
            validButton = Pair("Valider") { createRetroEvent.invoke() }
        )
        when (val res = state.retroId) {
            is Error -> Snackbar() {
                Text(text = res.msg)
            }
            is Loading -> CircularProgressIndicator()
            is Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.retroId.value.let { retroId ->
                        Text(
                            modifier = Modifier
                                .padding(8.dp)
                                .background(Color.Gray)
                                .clickable {
                                    clipboardManager.setText(AnnotatedString("https://retromariokmm.page.link/?link=https://www.example.com/$retroId&apn=com.example.retromariokmm.android"))
                                },
                            text = "https://retromariokmm.page.link/?link=https://www.example.com/$retroId&apn=com.example.retromariokmm.android"
                        )
                    }
                    OutlinedButton(onClick = {
                        addToRetroEvent.invoke()
                    }) {
                        Text(text = "Add You to this RETRO")
                        when (val action = state.addMeToTheRetroAction) {
                            NOT_STARTED -> {}
                            PENDING -> CircularProgressIndicator()
                            ERROR -> {
                                Snackbar() {
                                    Text(text = "Error add user to retro")
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
            else -> {}
        }
    }
}

@Preview
@Composable
fun RetroCreationScreenPreview() {
    RetroCreationScreen(
        navController = rememberNavController(),
        RetroCreationContainer(retroId = Success("")),
        {},
        {},
        {},
        {})
}