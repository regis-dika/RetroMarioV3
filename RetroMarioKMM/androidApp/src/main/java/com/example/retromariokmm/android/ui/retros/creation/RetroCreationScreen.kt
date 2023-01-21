package com.example.retromariokmm.android.ui.retros.creation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Success

@Composable
fun RetroCreationScreen(
    navController: NavController,
    viewModel: RetroCreationViewModel = hiltViewModel()
) {
    val state = viewModel.retroCreationState.collectAsState()
    when (val res = state.value) {
        is Error -> Snackbar() {
            Text(text = res.msg)
        }
        is Loading -> CircularProgressIndicator()
        is Success -> {
            Column(Modifier.fillMaxSize()) {
                Text(text = res.value.retroId)
            }
        }
    }
}