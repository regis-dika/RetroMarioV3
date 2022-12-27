package com.example.retromariokmm.android.ui.comments.details

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.retromariokmm.android.ui.components.TransparentHiltTextField
import com.example.retromariokmm.utils.ActionState
import com.example.retromariokmm.utils.ActionState.*

@Composable
fun CommentDetailsScreen(
    commentId: String,
    navController: NavController,
    viewModel: CommentDetailsViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState(initial = CommentDetailsState())

    val hasBeenSave  = state.value.saveActionState == SUCCESS
    LaunchedEffect(key1 = hasBeenSave) {
        if (hasBeenSave) {
            navController.popBackStack()
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            viewModel.saveComment()
        }, backgroundColor = Color.Black) {
            Icon(imageVector = Icons.Default.Check, contentDescription = "save note", tint = Color.White)
        }
    }) { padding ->
        Column(
            modifier = Modifier
                .background(Color.Magenta)
                .fillMaxSize()
                .padding(16.dp),
        ) {
            TransparentHiltTextField(
                text = "Title",
                hint = "Enter a title...",
                isHintVisible = false,
                onValueChange = {
                    //viewModel.onNoteTitleChanged(it)
                },
                onFocusChanged = {
                    //viewModel.onNoteTitleFocusChanged(it.isFocused)
                }, singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TransparentHiltTextField(
                text = state.value.description,
                hint = "Enter a content...",
                isHintVisible = false,
                onValueChange = {
                    viewModel.onChangeDescription(it)
                },
                onFocusChanged = {
                    //viewModel.onNoteContentFocusChanged(it.isFocused)
                }, singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier.weight(1f)  // occupy tje remaining space

            )
        }
    }
}