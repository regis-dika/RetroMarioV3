package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.unit.dp
import com.example.retromariokmm.android.ui.components.FeelingsState.*
import com.example.retromariokmm.domain.models.Feelings

@Composable
fun FeelingsCounterItem(
    nbLikes: Int = 0,
    nbDislikes: Int = 0,
    onLikeClick: (() -> Unit),
    onDisLikeClick: (() -> Unit),
) {

    val feelingsState = remember {
        mutableStateOf(FeelingsState.NOT_FEELINGS)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val likeColor = when (feelingsState.value) {
            LIKE -> Color.Blue
            else -> Color.LightGray
        }
        val disLikeColor = when (feelingsState.value) {
            DISLIKE -> Color.Red
            else -> Color.LightGray
        }
        IconButton(modifier = Modifier.background(likeColor), onClick = { onLikeClick.invoke()
        feelingsState.value = LIKE}) {
            Column(Modifier.wrapContentSize()) {
                Icon(Icons.Default.ThumbUp, contentDescription = "like")
                Text(text = nbLikes.toString())
            }
        }
        IconButton(modifier = Modifier.background(disLikeColor), onClick = { onDisLikeClick.invoke() }) {
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