package com.example.retromariokmm.android.ui.retros.creation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun RetroCreationScreen(
    navController: NavController,
    viewModel: RetroCreationViewModel = hiltViewModel()
) {
    Column(Modifier.fillMaxSize()) {
        Text(text = "Creation screen")
    }
}