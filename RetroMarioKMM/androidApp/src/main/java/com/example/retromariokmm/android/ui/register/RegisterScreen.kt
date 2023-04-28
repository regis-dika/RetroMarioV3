package com.example.retromariokmm.android.ui.register

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.retromariokmm.android.ui.register.RegisterActionState.Success
import com.example.retromariokmm.android.ui.register.RegisterActionState.SuccessLoginAndAddUser
import com.example.retromariokmm.utils.ActionState

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun RegisterScreen(
    retroId: String? = null,
    navController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val state by registerViewModel.state.collectAsState()

    //navigation not good handle if use in when with no launchEffect
    LaunchedEffect(key1 = state.saveState) {
        if (state.saveState == Success) {
            navController.navigate("retros_screen")
        }
        if (state.saveState == SuccessLoginAndAddUser) {
            navController.navigate("life_difficulty_screen")
        }
    }
    LaunchedEffect(key1 = Unit) {
        val retroIdentifier = if (retroId == "null") null else retroId
        registerViewModel.onRetroId(retroIdentifier)
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = "Email")
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .background(Color.LightGray),
            value = state.email,
            onValueChange = {
                registerViewModel.onEmailChange(it)
            })

        Spacer(modifier = Modifier.height(2.dp))

        Text(text = "Password")
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .background(Color.LightGray),
            value = state.password,
            onValueChange = {
                registerViewModel.onPasswordChange(it)
            })
        Text(text = "Firstname")
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .background(Color.LightGray),
            value = state.firstname,
            onValueChange = {
                registerViewModel.onFirstNameChange(it)
            })

        Spacer(modifier = Modifier.height(2.dp))

        Text(text = "Lastname")
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .background(Color.LightGray),
            value = state.name,
            onValueChange = {
                registerViewModel.onNameChange(it)
            })
        OutlinedButton(onClick = { registerViewModel.saveFormAndLogin() }) {
            Text(text = "Valider")
        }
    }
}
