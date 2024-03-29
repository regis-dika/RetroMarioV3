package com.example.retromariokmm.android.ui.register

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.retromariokmm.android.ui.register.RegisterActionState.Success
import com.example.retromariokmm.android.ui.register.RegisterActionState.SuccessLoginAndAddUser

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun RegisterScreen(
    retroId: String? = null,
    navController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (state.picturePath != null) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = Uri.parse(state.picturePath)
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Crop
                )
            }
            IconButton(modifier = Modifier.size(15.dp), onClick = {
                navController.navigate("camera_preview")
            }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
        }
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
