package com.example.retromariokmm.android.ui.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retromariokmm.android.ui.actions.list.ActionContainer
import com.example.retromariokmm.android.ui.components.FeelingsState.NOT_FEELINGS

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
            .background(backgroundColor)
            .clickable { onActionClick.invoke() }
            .padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    modifier = Modifier
                        .background(color)
                        .clickable(MutableInteractionSource(), null) {
                            onTakeActionClick.invoke(!actionContainer.currentActor)
                        },
                    imageVector = Icons.Default.Add,
                    contentDescription = "take action button"
                )
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)) {
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
                    Text(
                        text = actionContainer.actors,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp
                    )
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