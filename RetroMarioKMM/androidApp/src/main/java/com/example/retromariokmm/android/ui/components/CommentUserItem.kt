package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retromariokmm.android.MyApplicationTheme
import com.example.retromariokmm.android.ui.comments.list.CommentContainer
import com.example.retromariokmm.android.ui.components.FeelingsState.LIKE
import com.example.retromariokmm.android.ui.lifeanddifficulty.UserContainer
import com.example.retromariokmm.domain.models.UserComment

@Composable
fun CommentUserItem(
    commentContainer: CommentContainer,
    backgroundColor: Color,
    onNoteClick: () -> Unit,
    onLikeClick: (FeelingsState) -> Unit,
    onDisLikeClick: (FeelingsState) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    //only change when note.created change
    /*val formattedDate = remember(note.created) {
        DateTimeUtil.formatNoteDate(note.created)
    }*/

    Box(modifier = modifier) {
        Column(modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .clickable { onNoteClick.invoke() }
            .padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {//push all elements to the extremitate
                Text(
                    text = commentContainer.userComment.description,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Icon(
                    modifier = Modifier.clickable(MutableInteractionSource(), null) { onDeleteClick.invoke() },
                    imageVector = Icons.Default.Close,
                    contentDescription = "delete note button"
                )
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
                false,
                LIKE
            ), Color.Red, {}, {}, {}, {})
    }
}