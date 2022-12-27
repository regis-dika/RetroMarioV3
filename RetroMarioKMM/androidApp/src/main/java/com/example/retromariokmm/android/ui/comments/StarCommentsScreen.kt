package com.example.retromariokmm.android.ui.comments

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.retromariokmm.android.ui.components.CommentUserItem
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Success

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun StarCommentsScreen(starCommentsViewModel: StarCommentsViewModel = hiltViewModel()) {
    val state = starCommentsViewModel.commentsState.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp)
    ) {
        when (val list = state.value) {
            is Error -> Snackbar() {
                Text(text = list.msg)
            }
            is Loading -> {
                CircularProgressIndicator()
            }
            is Success -> LazyColumn() {
                items(list.value, key = {
                    it.userComment.postId
                }) { comment ->
                    CommentUserItem(
                        commentContainer = comment ,
                        backgroundColor = if (comment.isFromCurrentUser) Color.Red else Color.Cyan,
                        onNoteClick = { },
                        onDeleteClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                            .animateItemPlacement()
                    )
                }
            }
        }
    }
}
