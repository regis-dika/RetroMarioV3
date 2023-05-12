package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.retromariokmm.android.activity.MyApplicationTheme
import com.example.retromariokmm.android.helper.RetroBorder
import com.example.retromariokmm.android.ui.components.HealthyBoardModel.MainUser
import com.example.retromariokmm.android.ui.components.HealthyBoardModel.OtherUser
import com.example.retromariokmm.android.ui.lifeanddifficulty.UserContainer

@Composable
fun RetroUserItem(
    userContainer: UserContainer,
    healthyBoardModel: HealthyBoardModel,
    onSaveChange: (() -> Unit),
    modifier: Modifier = Modifier
) {
    //only change when note.created change
    /*val formattedDate = remember(note.created) {
        DateTimeUtil.formatNoteDate(note.created)
    }*/

    var isEditable by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .padding(8.dp)
            .RetroBorder()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            RetroProfilItem(
                picture = userContainer.picture,
                firstName = userContainer.firstName,
                lastName = userContainer.lastName,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent, shape = RoundedCornerShape(15.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            when (healthyBoardModel) {
                is MainUser -> {
                    HealthyMainBoardItem(mainUser = healthyBoardModel, isEditable)
                    OutlinedButton(onClick = {
                        if (isEditable) {
                            onSaveChange.invoke()
                        }
                        isEditable = !isEditable
                    }) {
                        Text(text = if (isEditable) "Valider" else "Editer")
                    }
                }
                is OtherUser -> HealthyOtherBoardItem(otherUser = healthyBoardModel)
            }
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
            OtherUser(), {})
    }
}