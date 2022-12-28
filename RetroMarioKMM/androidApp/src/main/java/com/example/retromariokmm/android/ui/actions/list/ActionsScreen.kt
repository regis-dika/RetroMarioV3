package com.example.retromariokmm.android.ui.actions.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.retromariokmm.android.ui.components.UserActionItem
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Success

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ActionsScreen(navController: NavController, viewModel: ActionsViewModel = hiltViewModel()) {
    val state = viewModel.actionsState.collectAsState(initial = Loading())
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(6.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedButton(onClick = {
                    //navController.navigate("comment_details_screen/ ")
                }) {
                    Text(text = "New Action")
                }
                OutlinedButton(onClick = {
                    //navController.navigate("actions_screen")
                }) {
                    Text(text = "Go to Updated HealthyScreen")
                }
            }

            when (val list = state.value) {
                is Error -> Snackbar() {
                    Text(text = list.msg)
                }
                is Loading -> {
                    CircularProgressIndicator()
                }
                is Success ->
                    if (list.value.isEmpty()) {
                        Text(modifier = Modifier.fillMaxSize(), text = "NO Actions")
                    } else {
                        LazyColumn() {
                            items(list.value, key = {
                                it.userAction.authorId
                            }) { action ->
                                UserActionItem(
                                    actionContainer = action,
                                    backgroundColor = if (action.isFromCurrentUser) Color.Red else Color.Cyan,
                                    onActionClick = {
                                        //navController.navigate("comment_details_screen/${comment.userComment.postId}")
                                    },
                                    onDeleteClick = { },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(6.dp)
                                        .animateItemPlacement(),
                                    onCheckClick = {
                                        //starCommentsViewModel.updateLikeComment(comment.userComment.postId, it.toFeelings())
                                    },
                                    onTakeActionClick = {
                                        //starCommentsViewModel.updateLikeComment(comment.userComment.postId, it.toFeelings())
                                    }
                                )
                            }
                        }
                    }
            }
        }
    }
}