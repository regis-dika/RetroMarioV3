package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.retromariokmm.android.MyApplicationTheme
import com.example.retromariokmm.android.ui.components.HealthyBoardModel.OtherUser

@Composable
fun BoardItem(
    modifier: Modifier = Modifier,
    imageId: Int,
    title: String,
    nbrElements: Int
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier
            .wrapContentWidth()
            .padding(8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .padding(2.dp)
                    .clip(RoundedCornerShape(15.dp)),
                painter = painterResource(id = imageId),
                contentDescription = "board item"
            )
            Column(
                Modifier.wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = title, fontWeight = FontWeight.Bold)
                Text(text = "$nbrElements comments")
            }
        }
    }
}

@Preview
@Composable
fun BoardItemPreview() {
    MyApplicationTheme() {
        BoardItem(
            imageId = com.example.retromariokmm.android.R.drawable.retro_board_item_star,
            title = "Past Success",
            nbrElements = 10
        )
    }
}