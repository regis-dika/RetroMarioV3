package com.example.retromariokmm.android.ui.comments.board

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.retromariokmm.android.ui.components.BoardItem
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp), verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(6.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BoardItem(
                        modifier = Modifier.size(70.dp)
                            .background(Color.Blue, shape = RoundedCornerShape(20.dp))
                            .clickable { navController.navigate("comments_screen/$STAR_COMMENTS") },
                        imageId = com.example.retromariokmm.android.R.drawable.retro_board_item_star,
                        nbrElements = state.value.value?.nbStarComments.toString()
                    )
                    BoardItem(
                        modifier = Modifier.size(70.dp)
                            .background(Color.Blue, shape = RoundedCornerShape(20.dp))
                            .clickable { navController.navigate("comments_screen/$BOO_COMMENTS") },
                        imageId = com.example.retromariokmm.android.R.drawable.retro_board_item_boo,
                        nbrElements = state.value.value?.nbBooComments.toString()
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(6.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BoardItem(
                        modifier = Modifier.size(70.dp)
                            .background(Color.Blue, shape = RoundedCornerShape(20.dp))
                            .clickable { navController.navigate("comments_screen/$GOOMBA_COMMENTS") },
                        imageId = com.example.retromariokmm.android.R.drawable.retro_board_item_goomba,
                        nbrElements = state.value.value?.nbGoombaComments.toString()
                    )
                    BoardItem(
                        modifier = Modifier.size(70.dp)
                            .background(Color.Blue, shape = RoundedCornerShape(20.dp))
                            .clickable { navController.navigate("comments_screen/$MUSHROOM_COMMENTS") },
                        imageId = com.example.retromariokmm.android.R.drawable.retro_board_item_mushroom,
                        nbrElements = state.value.value?.nbMushroomComments.toString()
                    )

                }
            }
        }
    }
}