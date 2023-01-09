package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BoardItem(
    modifier: Modifier = Modifier,
    imageId: Int,
    nbrElements: String
) {
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        Column(Modifier.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .padding(2.dp)
                    .clip(RoundedCornerShape(15.dp)),
                painter = painterResource(id = imageId),
                contentDescription = "board item"
            )
            Text(text = nbrElements, modifier = Modifier.clip(RectangleShape))
        }
    }
}