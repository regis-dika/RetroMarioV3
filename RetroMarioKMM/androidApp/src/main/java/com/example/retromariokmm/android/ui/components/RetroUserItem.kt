package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.retromariokmm.android.MyApplicationTheme
import com.example.retromariokmm.android.ui.lifeanddifficulty.UserContainer

@Composable
fun RetroUserItem(
    userContainer: UserContainer,
    backgroundColor: Color = Color.White,
    onLikeClick:  (Int) -> Unit,
    onDifficultyClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    //only change when note.created change
    /*val formattedDate = remember(note.created) {
        DateTimeUtil.formatNoteDate(note.created)
    }*/

    Box(modifier = modifier) {
        Column(modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .clickable {}
            .padding(16.dp)) {
            RetroProfilItem(
                picture = userContainer.picture,
                firstName = userContainer.firstName,
                lastName = userContainer.lastName,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Green, shape = RoundedCornerShape(15.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            HealthyBoardItem(
                life = userContainer.life,
                difficulty = userContainer.difficulty,
                onLifeClick = {
                    onLikeClick.invoke(it)
                },
                onDifficultyClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
            )
        }
    }
}

@Preview
@Composable
fun RetroUserItemPreview() {
    MyApplicationTheme() {
        RetroUserItem(
            userContainer = UserContainer(
                "",
                "Régis",
                "Dika",
                "https://media.geeksforgeeks.org/wp-content/uploads/20210101144014/gfglogo.png",
                1,
                10,
                false
            ),
           onDifficultyClick = {}, onLikeClick = {})
    }
}