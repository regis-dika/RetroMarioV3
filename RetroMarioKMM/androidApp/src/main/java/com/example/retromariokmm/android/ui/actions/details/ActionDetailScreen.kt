package com.example.retromariokmm.android.ui.actions.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.retromariokmm.android.ui.components.DoubleTextFieldItem
import com.example.retromariokmm.android.ui.components.TransparentHiltTextField
import com.example.retromariokmm.utils.ActionState.*

@Composable
fun ActionDetailsScreen(
    actionId: String,
    navController: NavController,
    state: ActionDetailsState,
    onTitleChange: ((String) -> Unit),
    onDescriptionChange: ((String) -> Unit),
    validateEvent: (() -> Unit),
    modifier: Modifier = Modifier
) {

    val hasBeenSave = state.saveActionState == SUCCESS
    LaunchedEffect(key1 = hasBeenSave) {
        if (hasBeenSave) {
            navController.popBackStack()
        }
    }
    Surface(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)) {
        DoubleTextFieldItem(
            firstEdiTitleValue = Pair("Title", state.title),
            onFirstEdtChange = {
                onTitleChange.invoke(it)
            },
            secondEdiTitleValue = Pair("Description", state.description),
            onSecondEdtChange = {
                onDescriptionChange.invoke(it)
            },
            validButton = Pair("Valider") {
                validateEvent.invoke()
            }
        )
    }
}

@Preview
@Composable
fun ActionDetailsScreenPreview() {
    ActionDetailsScreen(
        actionId = "",
        navController = rememberNavController(),
        state = ActionDetailsState(),
        {},
        {},
        {})
}
