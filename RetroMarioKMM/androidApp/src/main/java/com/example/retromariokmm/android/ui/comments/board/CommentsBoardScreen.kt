package com.example.retromariokmm.android.ui.comments.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.retromariokmm.utils.*

@Composable
fun CommentsBoardScreen(navController: NavController, viewModel: CommentsBoardViewModel = hiltViewModel()) {

    val state = viewModel.boardState.collectAsState(initial = Loading())

    Box(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                OutlinedButton(onClick = { navController.navigate("actions_screen") }) {
                    Text(text = "Go to ACTION")
                }
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(6.dp), verticalArrangement = Arrangement.SpaceBetween) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(6.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        modifier = Modifier.background(Color.Blue),
                        onClick = { navController.navigate("comments_screen/$STAR_COMMENTS") }) {
                        Column(Modifier.wrapContentSize()) {
                            Icon(Icons.Default.Star, contentDescription = "star")
                            Text(text = state.value.value?.nbStarComments.toString())
                        }
                    }
                    IconButton(
                        modifier = Modifier.background(Color.Blue),
                        onClick = { navController.navigate("comments_screen/$BOO_COMMENTS") }) {
                        Column(Modifier.wrapContentSize()) {
                            Icon(Icons.Default.Build, contentDescription = "boo")
                            Text(text = state.value.value?.nbBooComments.toString())
                        }
                    }
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(6.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        modifier = Modifier.background(Color.Blue),
                        onClick = { navController.navigate("comments_screen/$GOOMBA_COMMENTS") }) {
                        Column(Modifier.wrapContentSize()) {
                            Icon(Icons.Default.Warning, contentDescription = "goomba")
                            Text(text = state.value.value?.nbGoombaComments.toString())
                        }
                    }
                    IconButton(
                        modifier = Modifier.background(Color.Blue),
                        onClick = { navController.navigate("comments_screen/$MUSHROOM_COMMENTS") }) {
                        Column(Modifier.wrapContentSize()) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "mushroom")
                            Text(text = state.value.value?.nbMushroomComments.toString())
                        }
                    }
                }
            }
        }
    }
}