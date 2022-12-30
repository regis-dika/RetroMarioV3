package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.retromariokmm.android.ui.components.FeelingsState.*

@Composable
fun HealthyBoardItem(
    life: Int = 0,
    difficulty: Int = 0,
    onLifeClick: (() -> Unit),
    onDifficultyClick: (() -> Unit),
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            modifier = Modifier
                .background(Color.Green)
                .clip(RectangleShape)
                .border(2.dp, Color.LightGray),
            onClick = { onLifeClick.invoke() }) {
            Column(Modifier.wrapContentSize()) {
                Icon(Icons.Default.Star, contentDescription = "life")
                Text(text = life.toString())
            }
        }
        IconButton(
            modifier = Modifier
                .background(Color.Magenta)
                .clip(RectangleShape)
                .border(2.dp, Color.LightGray),
            onClick = { onDifficultyClick.invoke() }) {
            Column(Modifier.wrapContentSize()) {
                Icon(Icons.Default.Warning, contentDescription = "difficulty")
                Text(text = difficulty.toString())
            }
        }
    }
}
