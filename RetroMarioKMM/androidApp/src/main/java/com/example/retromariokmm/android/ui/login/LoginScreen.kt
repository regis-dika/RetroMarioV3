package com.example.retromariokmm.android.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.retromariokmm.android.ui.login.LoginState.*

@Composable
fun LoginScreen(
    retroId: String?,
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
    }

    Column(modifier = Modifier.fillMaxSize()) {
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
            Snackbar(modifier = Modifier.fillMaxWidth()) {
                Text(text = retroId)
            }
        }
        
        OutlinedButton(onClick = {viewModel.onLogin()}) {
            Text(text = "Go to login")
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
        }
    }

}