package com.example.retromariokmm.android.ui.comments.board

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.retromariokmm.android.activity.AppBarIcons.Next
import com.example.retromariokmm.android.activity.Screen.*
import com.example.retromariokmm.android.ui.components.BoardItem
import com.example.retromariokmm.utils.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun CommentsBoardScreen(navController: NavController, viewModel: CommentsBoardViewModel = hiltViewModel()) {

    val state = viewModel.boardState.collectAsState(initial = Loading())

    LaunchedEffect(key1 = Unit){
        CommentBoard.buttons.onEach {
            when(it){
                Next -> navController.navigate("actions_screen")
                else -> {}
            }
        }.launchIn(this)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp), verticalArrangement = Arrangement.SpaceAround
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(6.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BoardItem(
                        modifier = Modifier
                            .clickable { navController.navigate("comments_screen/$STAR_COMMENTS") },
                        imageId = com.example.retromariokmm.android.R.drawable.retro_board_item_star,
                        title = "Past Success",
                        nbrElements = state.value.value?.nbStarComments ?: 0
                    )
                    BoardItem(
                        modifier = Modifier
                            .clickable { navController.navigate("comments_screen/$BOO_COMMENTS") },
                        imageId = com.example.retromariokmm.android.R.drawable.retro_board_item_boo,
                        title = "Futur Risk",
                        nbrElements = state.value.value?.nbBooComments ?: 0
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(6.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BoardItem(
                        modifier = Modifier
                            .clickable { navController.navigate("comments_screen/$GOOMBA_COMMENTS") },
                        imageId = com.example.retromariokmm.android.R.drawable.retro_board_item_goomba,
                        title = "Past Failed",
                        nbrElements = state.value.value?.nbGoombaComments ?: 0
                    )
                    BoardItem(
                        modifier = Modifier
                            .clickable { navController.navigate("comments_screen/$MUSHROOM_COMMENTS") },
                        imageId = com.example.retromariokmm.android.R.drawable.retro_board_item_mushroom,
                        title = "Futur opportunities",
                        nbrElements = state.value.value?.nbMushroomComments ?: 0
                    )

                }
            }
        }
    }
}