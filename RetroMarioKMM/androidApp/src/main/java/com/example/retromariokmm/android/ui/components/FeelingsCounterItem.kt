package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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
fun FeelingsCounterItem(
    feelingsState: FeelingsState,
    nbLikes: Int = 0,
    nbDislikes: Int = 0,
    onLikeClick: ((FeelingsState) -> Unit),
    onDisLikeClick: ((FeelingsState) -> Unit),
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val colorPair = remember {
            mutableStateOf(Pair(Color.LightGray, Color.LightGray))
        }
        val clickPair = remember {
            mutableStateOf(Pair(NOT_FEELINGS, NOT_FEELINGS))
        }

        colorPair.value = when (feelingsState) {
            LIKE -> Pair(Color.Blue, Color.LightGray)
            DISLIKE -> Pair(Color.LightGray, Color.Red)
            NOT_FEELINGS -> Pair(Color.LightGray, Color.LightGray)
        }
        clickPair.value = when (feelingsState) {
            LIKE -> Pair(NOT_FEELINGS, DISLIKE)
            DISLIKE -> Pair(LIKE, NOT_FEELINGS)
            NOT_FEELINGS -> Pair(LIKE, DISLIKE)
        }
        IconButton(
            modifier = Modifier
                .background(colorPair.value.first)
                .clip(RectangleShape)
                .border(2.dp, Color.LightGray),
            onClick = { onLikeClick.invoke(clickPair.value.first) }) {
            Column(Modifier.wrapContentSize()) {
                Icon(Icons.Default.ThumbUp, contentDescription = "like")
                Text(text = nbLikes.toString())
            }
        }
        IconButton(
            modifier = Modifier
                .background(colorPair.value.second)
                .clip(RectangleShape)
                .border(2.dp, Color.LightGray),
            onClick = { onDisLikeClick.invoke(clickPair.value.second) }) {
            Column(Modifier.wrapContentSize()) {
                Icon(Icons.Default.Warning, contentDescription = "dislike")
                Text(text = nbDislikes.toString())
            }
        }
    }
}

enum class FeelingsState {
    LIKE, DISLIKE, NOT_FEELINGS
}

fun FeelingsState.toFeelings(): Boolean? {
    return when (this) {
        LIKE -> true
        DISLIKE -> false
        NOT_FEELINGS -> null
    }
}