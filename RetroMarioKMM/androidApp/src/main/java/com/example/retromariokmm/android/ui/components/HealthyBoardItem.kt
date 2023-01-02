package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.retromariokmm.android.MyApplicationTheme
import com.example.retromariokmm.android.R

@Composable
fun HealthyBoardItem(
    life: Int = 0,
    difficulty: Int = 0,
    onLifeClick: ((Int) -> @Composable Unit)? = null,
    onDifficultyClick: ((Int) -> Unit),
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ) {
        IconButton(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Cyan)
                .clip(RectangleShape),
            onClick = { onLifeClick?.invoke(life) }) {
            Column(Modifier.wrapContentSize(), verticalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.retro_life_icon),
                    contentDescription = "life",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(20.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                LinearProgressIndicator(
                    progress = (life.toFloat() / 10),
                    color = Color.Blue,
                    backgroundColor = Color.White
                )
            }
        }
        IconButton(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Magenta)
                .clip(RectangleShape),
            onClick = { onDifficultyClick.invoke(difficulty) }) {
            Column(Modifier.wrapContentSize(), verticalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.retro_difficulty_icon),
                    contentDescription = "difficulty",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(20.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                LinearProgressIndicator(
                    progress = (difficulty.toFloat() / 10),
                    color = Color.Red,
                    backgroundColor = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun HealthyBoardItemPreview() {
    MyApplicationTheme() {
        HealthyBoardItem( onDifficultyClick = {})
    }
}
