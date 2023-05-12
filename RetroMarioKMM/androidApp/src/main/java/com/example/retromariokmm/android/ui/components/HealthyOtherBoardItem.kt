package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.retromariokmm.android.R
import com.example.retromariokmm.android.activity.MyApplicationTheme
import com.example.retromariokmm.android.ui.components.HealthyBoardModel.OtherUser

@Composable
fun HealthyOtherBoardItem(
    otherUser: OtherUser,
    modifier: Modifier = Modifier
) {
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
            LinearProgressIndicator(
                progress = (otherUser.life.toFloat() / 10),
                color = Color.Blue,
                backgroundColor = Color.White
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
            LinearProgressIndicator(
                progress = (otherUser.difficulty.toFloat() / 10),
                color = Color.Red,
                backgroundColor = Color.White
            )
        }
    }
}

@Preview
@Composable
fun HealthyBoardItemPreview() {
    MyApplicationTheme() {
        HealthyOtherBoardItem(OtherUser())
    }
}

sealed class HealthyBoardModel(open val life: Int = 0,
                               open val difficulty: Int = 0) {
    data class MainUser(
        override val life: Int = 0,
        override val difficulty: Int = 0,
        val onLifeClick: ((Int) -> Unit),
        val onDifficultyClick: ((Int) -> Unit),
    ) : HealthyBoardModel(life, difficulty)

    data class OtherUser(
        override val life: Int = 0,
        override val difficulty: Int = 0,
    ) : HealthyBoardModel(life,difficulty)
}
