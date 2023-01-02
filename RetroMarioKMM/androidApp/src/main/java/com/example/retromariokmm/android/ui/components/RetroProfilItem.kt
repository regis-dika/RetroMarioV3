package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.TextStyle.Companion
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.retromariokmm.android.MyApplicationTheme
import com.example.retromariokmm.android.R.drawable
import com.example.retromariokmm.android.ui.lifeanddifficulty.UserContainer

@Composable
fun RetroProfilItem(
    picture: String,
    firstName: String,
    lastName: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {//push all elements to the extremitate
        AsyncImage(
            model = picture,
            contentDescription = "picture",
            error = painterResource(drawable.default_profil_picture),
            modifier = Modifier
                .size(60.dp)
                .padding(4.dp)
                .wrapContentSize()
        )
        Column(
            modifier = Modifier
                .padding(4.dp)
                .wrapContentSize()
        ) {
            Text(text = firstName, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
            Text(text = lastName, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        }
    }
}

@Preview
@Composable
fun RetroProfilItemPreview() {
    MyApplicationTheme() {
        RetroProfilItem(
            "",
            "Régis",
            "Dika",
            Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .clip(RoundedCornerShape(15.dp))
        )
    }
}