package com.example.retromariokmm.android.ui.retros.creation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.retromariokmm.utils.ActionState.*
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Success

@Composable
fun RetroCreationScreen(
    navController: NavController,
    viewModel: RetroCreationViewModel = hiltViewModel()
) {
    val state = viewModel.retroCreationState.collectAsState()
    val clipboardManager = LocalClipboardManager.current

    //navigation not good handle if use in when with no launchEffect
    LaunchedEffect(key1 = state.value.addMeToTheRetroAction) {
        if (state.value.addMeToTheRetroAction == SUCCESS) {
            navController.navigate("life_difficulty_screen")
        }
    }

    when (val res = state.value.retroId) {
        is Error -> Snackbar() {
            Text(text = res.msg)
        }
        is Loading -> CircularProgressIndicator()
        is Success -> {
            Column(Modifier.fillMaxSize()) {
                state.value.retroId.value.let { retroId ->
                    Text(
                        modifier = Modifier.clickable {
                            clipboardManager.setText(AnnotatedString("https://retromariokmm.page.link/?link=https://www.example.com/$retroId&apn=com.example.retromariokmm.android"))
                        },
                        text = "https://retromariokmm.page.link/?link=https://www.example.com/$retroId&apn=com.example.retromariokmm.android"
                    )
                }
                OutlinedButton(onClick = {
                    viewModel.addMeToThisRetro()
                }) {
                    Text(text = "Add You to this RETRO")
                    when (val action = state.value.addMeToTheRetroAction) {
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
    }
}