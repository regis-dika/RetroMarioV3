package com.example.retromariokmm.android.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retromariokmm.android.MyApplicationTheme
import com.example.retromariokmm.android.helper.RetroBorder
import com.example.retromariokmm.android.ui.comments.list.CommentContainer
import com.example.retromariokmm.android.ui.components.FeelingsState.LIKE
import com.example.retromariokmm.android.ui.lifeanddifficulty.UserContainer
import com.example.retromariokmm.domain.models.UserComment

@Composable
fun CommentUserItem(
    commentContainer: CommentContainer,
    onLikeClick: (FeelingsState) -> Unit,
    onDisLikeClick: (FeelingsState) -> Unit,
    onEditChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onValidClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    //only change when note.created change
    /*val formattedDate = remember(note.created) {
        DateTimeUtil.formatNoteDate(note.created)
    }*/

    val isEditable = remember {
        mutableStateOf(false)
    }

    val editableDescription = remember {
        mutableStateOf(commentContainer.userComment.description)
    }
    Box(modifier = modifier.RetroBorder()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            AnimatedVisibility(visible = isEditable.value) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) { //push all elements to the extremitate
                    OutlinedTextField(value = editableDescription.value, onValueChange = {
                        editableDescription.value = it
                        onEditChange.invoke(it)
                    })
                    Icon(
                        modifier = Modifier.clickable(MutableInteractionSource(), null) {
                            onValidClick.invoke()
                            isEditable.value = false
                        },
                        imageVector = Icons.Default.Done,
                        contentDescription = "done comment button"
                    )
                }
            }
            AnimatedVisibility(visible = !isEditable.value) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) { //push all elements to the extremitate

                    Text(
                        text = commentContainer.userComment.description,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                    if (commentContainer.isFromCurrentUser) {
                        Icon(
                            modifier = Modifier.clickable(MutableInteractionSource(), null) {
                                isEditable.value = !isEditable.value
                            },
                            imageVector = Icons.Default.Edit,
                            contentDescription = "delete note button"
                        )
                    }
                }
            }
            FeelingsCounterItem(
                nbLikes = commentContainer.nbLikes?.size ?: 0,
                nbDislikes = commentContainer.nbDisLikes?.size ?: 0,
                onLikeClick = {
                    onLikeClick.invoke(it)
                }, onDisLikeClick = {
                    onDisLikeClick.invoke(it)
                }, feelingsState = commentContainer.feelingsFromCurrentUser
            )
        }
    }
}

@Preview
@Composable
fun CommentUserItemPreview() {
    MyApplicationTheme() {
        CommentUserItem(
            commentContainer = CommentContainer(
                UserComment("", "", "Faire des trfucs stylé", null),
                true,
                LIKE
            ), {}, {}, {}, {}, {})
    }
}