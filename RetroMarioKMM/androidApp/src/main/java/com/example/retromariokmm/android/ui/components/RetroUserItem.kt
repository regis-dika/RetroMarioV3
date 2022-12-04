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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retromariokmm.android.ui.lifeanddifficulty.UserContainer

@Composable
fun NoteItem(
    userContainer: UserContainer,
    backgroundColor: Color,
    onNoteClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    //only change when note.created change
    /*val formattedDate = remember(note.created) {
        DateTimeUtil.formatNoteDate(note.created)
    }*/

    Box(modifier = modifier) {
        Column(modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(backgroundColor)
            .clickable { onNoteClick.invoke() }
            .padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {//push all elements to the extremitate
                Text(text = userContainer.firstName, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                Icon(
                    modifier = Modifier.clickable(MutableInteractionSource(), null) { onDeleteClick.invoke() },
                    imageVector = Icons.Default.Close,
                    contentDescription = "delete note button"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = userContainer.lastName, fontWeight = FontWeight.Light)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = userContainer.life.toString(), color = Color.DarkGray, modifier = Modifier.align(Alignment.End))
        }
    }
}