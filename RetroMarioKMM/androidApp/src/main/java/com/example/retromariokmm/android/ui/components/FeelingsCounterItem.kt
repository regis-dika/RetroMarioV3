package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorFilter.Companion
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.retromariokmm.android.R
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
            onClick = { onLikeClick.invoke(clickPair.value.first) }) {
            Column(
                Modifier.wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.retro_thumb_up_vector),
                    contentDescription = "like",
                    modifier = Modifier
                        .size(30.dp),
                    colorFilter = ColorFilter.tint(colorPair.value.first)
                )
                Text(text = nbLikes.toString())
            }
        }
        IconButton(
            onClick = { onDisLikeClick.invoke(clickPair.value.second) }) {
            Column(
                Modifier.wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.retro_thumb_down_vector),
                    contentDescription = "dislike",
                    modifier = Modifier
                        .size(30.dp),
                    colorFilter = ColorFilter.tint(colorPair.value.second)
                )
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