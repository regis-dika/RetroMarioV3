package com.example.retromariokmm.android.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.retromariokmm.android.ui.login.LoginState.*

@Composable
fun LoginScreen(
    retroId: String? = null,
    navController: NavController,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {
    val credentialsState = viewModel.loginCredentials.collectAsState()
    val loginState = viewModel.loginState.collectAsState()

    //navigation not good handle if use in when with no launchEffect
    LaunchedEffect(key1 = loginState.value) {
        if (loginState.value is Success) {
            navController.navigate("retros_screen")
        }
        if (loginState.value is SuccessLoginAndAddUser) {
            navController.navigate("life_difficulty_screen")
        }
    }

    val registerBtn = if(retroId == null) "Register" else "Register with id"

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Email")
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .background(Color.LightGray),
            value = credentialsState.value.email,
            onValueChange = {
                viewModel.onEmailChange(it)
            })

        Spacer(modifier = Modifier.height(2.dp))

        Text(text = "Password")
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .background(Color.LightGray),
            value = credentialsState.value.password,
            onValueChange = {
                viewModel.onPasswordChange(it)
            })

        if (retroId != null) {
            viewModel.onRetroIdAvailable(retroId)
            Snackbar(modifier = Modifier.fillMaxWidth()) {
                Text(text = retroId)
            }
            OutlinedButton(onClick = { viewModel.onLoginWithRetroId() }) {
                Text(text = "Go to login with id")
            }
        } else {
            OutlinedButton(onClick = { viewModel.onLogin() }) {
                Text(text = "Go to login")
            }
        }
        OutlinedButton(onClick = {
            navController.navigate("register_screen/${retroId}")
        }) {
            Text(text = registerBtn)
        }
        when (val result = loginState.value) {
            is Error -> Snackbar() {
                Text(text = result.msg)
            }
            Idle -> {}
            Loading -> CircularProgressIndicator()
            is Success -> Snackbar() {
                Text(text = "Successful Login")
            }
            is SuccessLoginAndAddUser -> {
                Snackbar() {
                    Text(text = "Successful Login from deeplink")
                }
            }
        }
    }

}