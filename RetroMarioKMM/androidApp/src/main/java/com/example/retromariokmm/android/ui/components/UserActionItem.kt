package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retromariokmm.android.MyApplicationTheme
import com.example.retromariokmm.android.R
import com.example.retromariokmm.android.ui.actions.list.ActionContainer
import com.example.retromariokmm.android.ui.comments.list.CommentContainer
import com.example.retromariokmm.android.ui.components.FeelingsState.LIKE
import com.example.retromariokmm.android.ui.components.FeelingsState.NOT_FEELINGS
import com.example.retromariokmm.domain.models.ActionActor
import com.example.retromariokmm.domain.models.UserAction
import com.example.retromariokmm.domain.models.UserComment

@Composable
fun UserActionItem(
    actionContainer: ActionContainer,
    backgroundColor: Color,
    onActionClick: () -> Unit,
    onTakeActionClick: (Boolean) -> Unit,
    onCheckClick: (Boolean) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    //only change when note.created change
    /*val formattedDate = remember(note.created) {
        DateTimeUtil.formatNoteDate(note.created)
    }*/

    val color = if (actionContainer.currentActor) Color.Blue else Color.LightGray

    Card(modifier = modifier) {
        Column(modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(backgroundColor, shape = RoundedCornerShape(15.dp))
            .clickable { onActionClick.invoke() }
            .padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier
                        .clickable(MutableInteractionSource(), null) {
                            onTakeActionClick.invoke(!actionContainer.currentActor)
                        },
                    colorFilter = ColorFilter.tint(color),
                    painter = painterResource(id = R.drawable.retro_person_add),
                    contentDescription = "take action button"
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                ) {
                    Text(
                        text = actionContainer.userAction.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = actionContainer.userAction.description,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                    actionContainer.actors?.map { it.pictureUrl }?.let { ActionActorsComposable(actorsUrl = it) }
                }
                Checkbox(
                    checked = actionContainer.userAction.isCheck,
                    onCheckedChange = {
                        onCheckClick.invoke(it)
                    }
                )
            }

        }
    }
}

@Preview
@Composable
fun UserActionItemPreview() {
    MyApplicationTheme() {
        UserActionItem(
            modifier = Modifier
                .wrapContentSize()
                .padding(6.dp),
            actionContainer = ActionContainer(
                UserAction(
                    title = "First action", description = "Faire des actions", actorList = hashMapOf(
                        Pair(
                            "",
                            ActionActor("Régis", "")
                        )
                    )
                ), false, ""
            ),
            backgroundColor = Color.Cyan,
            onActionClick = { },
            onTakeActionClick = {},
            onCheckClick = {},
            onDeleteClick = { })
    }
}