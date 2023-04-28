package com.example.retromariokmm.android.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.retromariokmm.android.ui.components.DoubleTextFieldItem
import com.example.retromariokmm.android.ui.login.LoginState.*

@Composable
fun LoginScreen(
    retroId: String? = null,
    navController: NavController,
    credentialsState: LoginCredentials,
    loginState: LoginState,
    onEmailEvent: ((String) -> Unit),
    onPasswordEvent: ((String) -> Unit),
    onLoginEvent: (() -> Unit)
) {
    //navigation not good handle if use in when with no launchEffect
    LaunchedEffect(key1 = loginState) {
        if (loginState is Success) {
            navController.navigate("retros_screen")
        }
        if (loginState is SuccessLoginAndAddUser) {
            navController.navigate("life_difficulty_screen")
        }
    }

    val loginBtn = if (retroId == null) "Sign in" else "Sign in Retro : $retroId"
    val registerBtn = if (retroId == null) "Register" else "Register with id"

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DoubleTextFieldItem(
            firstEdiTitleValue = Pair("Email", credentialsState.email),
            onFirstEdtChange = { onEmailEvent.invoke(it) },
            secondEdiTitleValue = Pair("Password", credentialsState.password),
            onSecondEdtChange = { onPasswordEvent.invoke(it) },
            validButton = Pair(loginBtn) { onLoginEvent.invoke() }
        )
        OutlinedButton(onClick = {
            navController.navigate("register_screen/${retroId}")
        }) {
            Text(text = registerBtn)
        }
        when (loginState) {
            is Error -> Snackbar() {
                Text(text = loginState.msg)
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

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        navController = rememberNavController(),
        credentialsState = LoginCredentials("", ""),
        loginState = Idle,
        onEmailEvent = {},
        onPasswordEvent = {}) {
    }
}