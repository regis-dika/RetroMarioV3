package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.retromariokmm.android.R
import com.example.retromariokmm.android.activity.MyApplicationTheme
import com.example.retromariokmm.android.ui.components.HealthyBoardModel.MainUser

@Composable
fun HealthyMainBoardItem(
    mainUser: MainUser,
    isEditable: Boolean,
    modifier: Modifier = Modifier
) {
    val lifeSliderPosition: MutableState<Float> = remember { mutableStateOf(mainUser.life.toFloat()) }
    val difficultySliderPosition: MutableState<Float> = remember { mutableStateOf(mainUser.difficulty.toFloat()) }

    Column(
        modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.retro_life_icon),
                contentDescription = "life",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(20.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Slider(
                steps = 10,
                value = lifeSliderPosition.value,
                onValueChange = {
                    lifeSliderPosition.value = it
                    mainUser.onLifeClick.invoke(it.toInt())
                },
                valueRange = 0f..10f,
                onValueChangeFinished = {
                },
                colors = SliderDefaults.colors(
                    thumbColor = Color.Yellow,
                    activeTrackColor = Color.Blue,
                    disabledThumbColor = Color.Gray
                ),
                enabled = isEditable
            )
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.retro_difficulty_icon),
                contentDescription = "difficulty",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(20.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Slider(
                steps = 10,
                value = difficultySliderPosition.value,
                onValueChange = {
                    difficultySliderPosition.value = it
                    mainUser.onDifficultyClick.invoke(it.toInt())
                },
                valueRange = 0f..10f,
                onValueChangeFinished = {
                },
                colors = SliderDefaults.colors(
                    thumbColor = Color.Yellow,
                    activeTrackColor = Color.Blue,
                    disabledThumbColor = Color.Gray
                ),
                enabled = isEditable
            )
        }
    }
}

@Preview
@Composable
fun HealthyMainBoardItemPreview() {
    MyApplicationTheme() {
        HealthyMainBoardItem(MainUser(life = 6, onLifeClick = {}, onDifficultyClick = {}), isEditable = false)
    }
}
