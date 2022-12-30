package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.retromariokmm.android.MyApplicationTheme
import com.example.retromariokmm.android.ui.lifeanddifficulty.UserContainer

@Composable
fun RetroUserItem(
    userContainer: UserContainer,
    backgroundColor: Color = Color.Cyan,
    onUserClick: () -> Unit,
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
            .clickable { onUserClick.invoke() }
            .padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {//push all elements to the extremitate
                AsyncImage(
                    model = userContainer.picture,
                    contentDescription = "picture",
                    error = painterResource(com.example.retromariokmm.android.R.drawable.default_profil_picture),
                    modifier = Modifier.size(60.dp)
                )
                Column(modifier = Modifier
                    .padding(4.dp)) {
                    Text(text = userContainer.firstName, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                    Text(text = userContainer.lastName, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                }
            }
            HealthyBoardItem(life = userContainer.life, difficulty = userContainer.difficulty, onLifeClick = { }) {
            }
        }
    }
}

@Preview
@Composable
fun RetroUserItemPreview() {
    MyApplicationTheme() {
        RetroUserItem(
            userContainer = UserContainer("", "Régis", "Dika", "https://media.geeksforgeeks.org/wp-content/uploads/20210101144014/gfglogo.png", 1, 10, false),
            onUserClick = { /*TODO*/ })
    }
}