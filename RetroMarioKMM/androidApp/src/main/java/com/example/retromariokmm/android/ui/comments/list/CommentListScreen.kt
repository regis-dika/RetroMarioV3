package com.example.retromariokmm.android.ui.comments.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.retromariokmm.android.ui.comments.list.CommentListEvent.*
import com.example.retromariokmm.android.ui.components.CommentUserItem
import com.example.retromariokmm.android.ui.components.toFeelings
import com.example.retromariokmm.utils.ActionState
import com.example.retromariokmm.utils.ActionState.*
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Success

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun CommentListScreen(
    path: String,
    navController: NavController,
    commentsState: CommentsScreen,
    newCommentState: NewCommentState,
    event: ((CommentListEvent) -> Unit),
    onSuccessAction: ((String) -> Unit)
) {
    val editComment = commentsState.saveActionState
    val createComment = newCommentState.saveActionState
    LaunchedEffect(editComment) {
        if (editComment == ActionState.SUCCESS) {
            onSuccessAction.invoke("Comment edited with success")
        }
    }
    LaunchedEffect(createComment) {
        if (createComment == ActionState.SUCCESS) {
            onSuccessAction.invoke("Comment created with success")
        }
    }
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
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .background(Color.LightGray),
                value = newCommentState.description,
                onValueChange = {
                    event.invoke(CurrentDescriptionEvent(it))
                })
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                OutlinedButton(onClick = { event.invoke(CreateCommentEvent) }) {
                    Text(text = "Valider")
                }
            }
            when (newCommentState.saveActionState) {
                ERROR -> Snackbar() {
                    Text(text = "Error on create comment")
                }
                PENDING -> {
                    CircularProgressIndicator()
                }
                else -> {}
            }
            when (newCommentState.saveActionState) {
                ERROR -> Snackbar() {
                    Text(text = "Error on edit comment")
                }
                PENDING -> {
                    CircularProgressIndicator()
                }
                else -> {}
            }
            when (val list = commentsState.comments) {
                is Error -> Snackbar() {
                    Text(text = list.msg)
                }
                is Loading -> {
                    CircularProgressIndicator()
                }
                is Success ->
                    if (list.value.isEmpty()) {
                        Text(modifier = Modifier.fillMaxSize(), text = "NO Comments")
                    } else {
                        LazyColumn() {
                            items(list.value, key = {
                                it.userComment.id
                            }) { comment ->
                                CommentUserItem(
                                    commentContainer = comment,
                                    onDeleteClick = { },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(6.dp)
                                        .animateItemPlacement(),
                                    onLikeClick = {
                                        event.invoke(
                                            OnLikeEvent(
                                                comment.userComment.id,
                                                it.toFeelings()
                                            )
                                        )
                                    },
                                    onDisLikeClick = {
                                        event.invoke(
                                            OnLikeEvent(
                                                comment.userComment.id,
                                                it.toFeelings()
                                            )
                                        )
                                    },
                                    onEditChange = {
                                        event.invoke(EditDescriptionEvent(it))
                                    },
                                    onValidClick = {
                                        event.invoke(EditCommentEvent(comment.userComment.id))
                                    }
                                )
                            }
                        }
                    }
            }
        }
    }
}

sealed class CommentListEvent {
    data class EditCommentEvent(val commentId: String) : CommentListEvent()
    data class EditDescriptionEvent(val description: String) : CommentListEvent()
    data class OnLikeEvent(val commentId: String, val feeling: Boolean?) : CommentListEvent()
    object CreateCommentEvent : CommentListEvent()
    data class CurrentDescriptionEvent(val description: String) : CommentListEvent()
}
